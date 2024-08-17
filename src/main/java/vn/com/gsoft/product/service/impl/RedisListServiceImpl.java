package vn.com.gsoft.product.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.com.gsoft.product.constant.CachingConstant;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaData;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaRes;
import vn.com.gsoft.product.service.RedisListService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RedisListServiceImpl implements RedisListService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    // Lấy toàn bộ danh sách giá trị
    public List<Object> getGiaoDichHangHoaValues(GiaoDichHangHoaReq req) {
        var fromDate = Double.parseDouble(new SimpleDateFormat("yyyyMMdd").format(req.getFromDate()));
        var toDate = Double.parseDouble(new SimpleDateFormat("yyyyMMdd").format(req.getToDate()));
        Set<Object> redisResults = redisTemplate.opsForZSet().rangeByScore("transactions", fromDate, toDate);
        Set<GiaoDichHangHoaData> results = new HashSet<>();

        if (redisResults != null) {
            for (Object json : redisResults) {
                GiaoDichHangHoaData data = objectMapper.convertValue(json, GiaoDichHangHoaData.class);
                results.add(data);
            }
        }
        return Arrays.asList(results.toArray());
    }
    @Override
    public void pushDataRedis(List<GiaoDichHangHoa> giaoDichHangHoas){
        giaoDichHangHoas.forEach(x->{
           saveTransaction(x.getId(), x);
        });
    }
   @Override
    public List<GiaoDichHangHoaData> getTransactionDetails(GiaoDichHangHoaReq req) {
       var fromDate = Double.parseDouble(new SimpleDateFormat("yyyyMMdd").format(req.getFromDate()));
       var toDate = Double.parseDouble(new SimpleDateFormat("yyyyMMdd").format(req.getToDate()));
       Set<Object> transactionIds = redisTemplate.opsForZSet().rangeByScore(CachingConstant.GIAO_DICH_HANG_HOA_THEO_NGAY, fromDate, toDate);

       return transactionIds.stream()
                .map(id -> {
                    Map<Object, Object> data = redisTemplate.opsForHash().entries(CachingConstant.GIAO_DICH_HANG_HOA + id);
                    return mapToGiaoDichHangHoaData(data);
                })
                .collect(Collectors.toList());
    }

    private GiaoDichHangHoaData mapToGiaoDichHangHoaData(Map<Object, Object> data) {
        GiaoDichHangHoaData giaoDich = new GiaoDichHangHoaData();
        giaoDich.setId(Long.parseLong((String) data.get("id")));
        giaoDich.setThuocId(Long.parseLong((String) data.get("thuocId")));
        giaoDich.setTenThuoc((String) data.get("tenThuoc"));
        giaoDich.setNhomThuocId(Integer.parseInt((String) data.get("nhomThuocId")));
        giaoDich.setTenNhomThuoc((String) data.get("tenNhomThuoc"));
        giaoDich.setNhomDuocLyId(Integer.parseInt((String) data.get("nhomDuocLyId")));
        giaoDich.setTenNhomDuocLy((String) data.get("tenNhomDuocLy"));
        giaoDich.setSoLuong(new BigDecimal((String) data.get("soLuong")));
        giaoDich.setGiaNhap(new BigDecimal((String) data.get("giaNhap")));
        giaoDich.setGiaBan(new BigDecimal((String) data.get("giaBan")));
        giaoDich.setTenDonVi((String) data.get("tenDonVi"));
        giaoDich.setTenHoatChat((String) data.get("tenHoatChat"));
        giaoDich.setNgayGiaoDich(new Date((String) data.get("ngayGiaoDich")));
        giaoDich.setDongBang(Boolean.parseBoolean((String) data.get("dongBang")));
        giaoDich.setLoaiGiaoDich(Integer.parseInt((String) data.get("LoaiGiaoDich")));
        giaoDich.setMaCoSo((String) data.get("maCoSo"));
        giaoDich.setSoLuongQuyDoi(new BigDecimal((String) data.get("soLuongQuyDoi")));
        return giaoDich;
    }

    private void saveTransaction(Long id, GiaoDichHangHoa data) {
        var timestamp = Double.parseDouble(new SimpleDateFormat("yyyyMMdd").format(data.getNgayGiaoDich()));
        String hashKey = "transaction-production:" + id;

        // Lưu thông tin chi tiết vào Redis Hash
        Map<String, String> hash = new HashMap<>();
        hash.put("thuocId", String.valueOf(data.getThuocId()));
        hash.put("tenThuoc", data.getTenThuoc());
        hash.put("nhomThuocId", String.valueOf(data.getNhomThuocId()));
        hash.put("tenNhomThuoc", data.getTenNhomThuoc());
        hash.put("nhomDuocLyId", String.valueOf(data.getNhomDuocLyId()));
        hash.put("tenNhomDuocLy", data.getTenNhomDuocLy());
        hash.put("soLuong", data.getSoLuong().toString());
        hash.put("giaNhap", data.getGiaNhap().toString());
        hash.put("giaBan", data.getGiaBan().toString());
        hash.put("tenDonVi", data.getTenDonVi());
        hash.put("tenHoatChat", data.getTenHoatChat());
        hash.put("ngayGiaoDich", data.getNgayGiaoDich().toString());
        hash.put("maCoSo", data.getMaCoSo());

        redisTemplate.opsForHash().putAll(hashKey, hash);

        // Thêm vào Sorted Sets
        redisTemplate.opsForZSet().add("transaction-production:date:" + data.getId(), id, timestamp);
        redisTemplate.opsForZSet().add("transaction-production:drug:" + data.getThuocId(), id, 0);
        redisTemplate.opsForZSet().add("transaction-production:group:" + data.getNhomThuocId(), id, 0);
        redisTemplate.opsForZSet().add("transaction-production:drugGroup:" + data.getNhomDuocLyId(), id, 0);
    }

}
