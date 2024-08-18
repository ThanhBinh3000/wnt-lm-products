package vn.com.gsoft.product.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.product.entity.*;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaRes;
import vn.com.gsoft.product.model.dto.HangHoaRep;
import vn.com.gsoft.product.model.dto.HangHoaRes;
import vn.com.gsoft.product.model.system.Profile;
import vn.com.gsoft.product.repository.*;
import vn.com.gsoft.product.service.HangHoaService;
import vn.com.gsoft.product.service.RedisListService;
import vn.com.gsoft.product.util.system.DataUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class HangHoaServiceImpl extends BaseServiceImpl<HangHoa, HangHoaRep, Long> implements HangHoaService {


    private HangHoaRepository hdrRepo;
    private GiaoDichHangHoaRepository giaoDichHangHoaRepository;
    @Autowired
    private RedisListService redisListService;

    @Autowired
    public HangHoaServiceImpl(HangHoaRepository hdrRepo, GiaoDichHangHoaRepository hangHoaRepository
                                ) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.giaoDichHangHoaRepository = hangHoaRepository;
    }
    @Override
    public Page<HangHoa> searchPageHangHoa(HangHoaRep req) throws Exception{
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        //lấy ra thông tin thuốc
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        Page<HangHoa> hangHoas = hdrRepo.searchPage(req, pageable);

        List<Long> thuocIds = hangHoas.stream().map(HangHoa::getThuocId).collect(Collectors.toList());
        var list = redisListService.getTransactionsByDateAndThuocIds(req, thuocIds);

        Calendar dateArchive = Calendar.getInstance();
        dateArchive.add(Calendar.YEAR, -1);
        //kiểm tra xem thời gian xem báo cáo có lớn hơn thời điểm archive không;
        if(req.getFromDate().before(dateArchive.getTime())){
            GiaoDichHangHoaReq giaoDichHangHoaReq  = new GiaoDichHangHoaReq();
            giaoDichHangHoaReq.setToDate(dateArchive.getTime());
            giaoDichHangHoaReq.setThuocids(thuocIds);
            var listArchive = giaoDichHangHoaRepository.searchList(giaoDichHangHoaReq);
            if(!listArchive.stream().isParallel()){
                list.addAll(listArchive);
            }
        }

        Map<Long, GiaoDichHangHoaRes> data = list.stream()
                .collect(Collectors.groupingBy(
                        GiaoDichHangHoa::getThuocId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                x -> {
                                    if (x.isEmpty()) {
                                        return null;
                                    }
                                    // Lọc dữ liệu cơ sở theo maCoSo
                                    List<GiaoDichHangHoa> duLieuCoSo = x.stream()
                                            .filter(item -> item.getMaCoSo().equals(userInfo.getMaCoSo()))
                                            .toList();

                                    // Tính tổng doanh số cơ sở
                                    BigDecimal doanhSoCoSo = duLieuCoSo.stream()
                                            .map(item -> item.getGiaBan().multiply(item.getSoLuong()))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    return new GiaoDichHangHoaRes(
                                            x.get(0).getTenDonVi(),
                                            x.stream()
                                                    .map(item -> item.getGiaBan().multiply(item.getSoLuong()))
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add),
                                            doanhSoCoSo,
                                            x.stream().map(GiaoDichHangHoa::getGiaBan).max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO),
                                            x.stream().map(GiaoDichHangHoa::getGiaBan).min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO),
                                            x.stream().map(GiaoDichHangHoa::getGiaNhap).max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO),
                                            x.stream().map(GiaoDichHangHoa::getGiaNhap).min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO)
                                    );
                                }
                        )
                ));
        if(!data.isEmpty()){
            hangHoas.getContent().forEach(x->{
                if(data.containsKey(x.getThuocId())){
                    x.setGiaBanMax(data.get(x.getThuocId()).getGiaBanMax());
                    x.setGiaBanMin(data.get(x.getThuocId()).getGiaBanMin());
                    x.setGiaNhapMax(data.get(x.getThuocId()).getGiaNhapMax());
                    x.setGiaNhapMin(data.get(x.getThuocId()).getGiaNhapMin());
                    x.setDoanhSoTT(data.get(x.getThuocId()).getSoLieuThiTruong());
                    x.setDoanhSoCS(data.get(x.getThuocId()).getSoLieuCoSo());
                    x.setTenDonVi(data.get(x.getThuocId()).getTenDonVi());
                }
            });
        }
        return hangHoas;
    }

    @Override
    public void pushTransactionData() {
        GiaoDichHangHoaReq req = new GiaoDichHangHoaReq();
        Calendar dateArchive = Calendar.getInstance();
        dateArchive.add(Calendar.MONDAY, -3);
        req.setFromDate(dateArchive.getTime());
        var data = giaoDichHangHoaRepository.searchList(req);

        redisListService.pushTransactionDataRedis(data.stream().toList());
    }

    @Override
    public void pushProductData() {
        HangHoaRep rep = new HangHoaRep();
        var data = hdrRepo.searchListHangHoa();
        redisListService.pushProductDataRedis(DataUtils.convertList(data, HangHoaRes.class));
    }

    @Override
    public List<HangHoaRes> getProductData() {
        var ids = new ArrayList<Long>();
        ids.add(9738374L);
        return redisListService.getHangHoaByIds(ids);
    }
}
