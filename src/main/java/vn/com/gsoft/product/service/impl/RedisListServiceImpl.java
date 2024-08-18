package vn.com.gsoft.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.com.gsoft.product.constant.CachingConstant;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.model.dto.*;
import vn.com.gsoft.product.model.dto.cache.HangHoaCache;
import vn.com.gsoft.product.service.RedisListService;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RedisListServiceImpl implements RedisListService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void pushTransactionDataRedis(List<GiaoDichHangHoa> giaoDichHangHoas) {
        giaoDichHangHoas.forEach(this::saveTransaction);
    }

    public List<GiaoDichHangHoa> getTransactionsByDateAndThuocIds(HangHoaRep req, List<Long> thuocIds) throws ParseException {
        double startTimestamp = req.getFromDate().getTime() / 1000.0;
        double endTimestamp = req.getToDate().getTime() / 1000.0;

        // 2. Lấy các transactionId theo ngày
        Set<Object> transactionIdsByDate = redisTemplate.opsForZSet().rangeByScore(CachingConstant.GIAO_DICH_HANG_HOA_THEO_NGAY, startTimestamp, endTimestamp);
        Set<Object> transactionIdsByThuoc = thuocIds.stream()
                .flatMap(thuocId -> redisTemplate.opsForSet().members(CachingConstant.GIAO_DICH_HANG_HOA_THEO_THUOC_ID + ":" + thuocId).stream())
                .collect(Collectors.toSet());
        transactionIdsByDate.retainAll(transactionIdsByThuoc);

        return transactionIdsByDate.stream()
                .map(id -> redisTemplate.opsForHash().entries(CachingConstant.GIAO_DICH_HANG_HOA + ":" + id))
                .map(data -> objectMapper.convertValue(data, GiaoDichHangHoa.class))
                .collect(Collectors.toList());
    }

    @Override
    public void pushProductDataRedis(List<HangHoaCache> hangHoas) {
           hangHoas.forEach(this::saveProductToRedis);
    }

    public List<HangHoaCache> getHangHoaByIds(List<Long> ids) {
        return ids.stream()
                .map(id -> {
                    Map<Object, Object> entries = redisTemplate.opsForHash().entries(CachingConstant.HANG_HOA + ":" + id);
                    return objectMapper.convertValue(entries, HangHoaCache.class);
                })
                .collect(Collectors.toList());
    }

    private void saveTransaction(GiaoDichHangHoa data) {
        double timestamp = data.getNgayGiaoDich().getTime() / 1000.0;
        // 1. Lưu thông tin giao dịch vào Redis Hash
        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("id", data.getId());
        transactionData.put("thuocId", data.getThuocId());
        transactionData.put("ngayGiaoDich", data.getNgayGiaoDich().getTime()); // Unix timestamp
        transactionData.put("soLuong", data.getSoLuong());
        transactionData.put("giaNhap", data.getGiaNhap());
        transactionData.put("giaBan", data.getGiaBan());
        transactionData.put("tenDonVi", data.getTenDonVi());
        transactionData.put("maCoSo", data.getMaCoSo());

        redisTemplate.opsForHash().putAll(CachingConstant.GIAO_DICH_HANG_HOA+":" + data.getId(), transactionData);

        redisTemplate.opsForZSet().add(CachingConstant.GIAO_DICH_HANG_HOA_THEO_NGAY, data.getId(), timestamp);

        redisTemplate.opsForSet().add(CachingConstant.GIAO_DICH_HANG_HOA_THEO_THUOC_ID + ":" + data.getThuocId(), data.getId());
    }

    private void saveProductToRedis(HangHoaCache data) {
        String key = CachingConstant.HANG_HOA + ":" + data.getThuocId();
        redisTemplate.opsForHash().putAll(key, objectMapper.convertValue(data, Map.class));
    }
}
