package vn.com.gsoft.product.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.product.constant.BaoCaoContains;
import vn.com.gsoft.product.constant.LimitPageConstant;
import vn.com.gsoft.product.entity.*;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaRes;
import vn.com.gsoft.product.model.system.Profile;
import vn.com.gsoft.product.repository.*;
import vn.com.gsoft.product.service.GiaoDichHangHoaService;
import vn.com.gsoft.product.service.RedisListService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class GiaoDichHangHoaServiceImpl extends BaseServiceImpl<GiaoDichHangHoa, GiaoDichHangHoaReq, Long> implements GiaoDichHangHoaService {


    private GiaoDichHangHoaRepository hdrRepo;
    @Autowired
    private RedisListService redisListService;

    @Autowired
    public GiaoDichHangHoaServiceImpl(GiaoDichHangHoaRepository hdrRepo
                                ) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }
    @Override
    public List<GiaoDichHangHoaRes> searchPageHangHoa(GiaoDichHangHoaReq req) throws Exception{
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setPageSize(req.getPageSize() == null || req.getPageSize() < 1 ? LimitPageConstant.DEFAULT : req.getPageSize());
        req.setLoaiBaoCao(req.getLoaiBaoCao() == null ? BaoCaoContains.MAT_HANG : req.getLoaiBaoCao());
        var list = redisListService.getTransactionDetails(req);

//        var items = list.stream()
//                .map(element->(GiaoDichHangHoa) element)
//                .collect(Collectors.toList());
//
//        Calendar dateArchive = Calendar.getInstance();
//        dateArchive.add(Calendar.YEAR, -1);
//        //kiểm tra xem thời gian xem báo cáo có lớn hơn thời điểm archive không;
//        if(req.getFromDate().before(dateArchive.getTime())){
//            req.setToDate(dateArchive.getTime());
//            var listArchive = hdrRepo.searchList(req);
//            if(!listArchive.stream().isParallel()){
//                items.addAll(listArchive);
//            }
//        }
//        List<GiaoDichHangHoaRes> data = new ArrayList<>();
//
//        data = items.stream()
//                .collect(Collectors.groupingBy(
//                        GiaoDichHangHoa::getThuocId,
//                        Collectors.collectingAndThen(
//                                Collectors.toList(),
//                                x -> {
//                                    if (x.isEmpty()) {
//                                        return null;
//                                    }
//                                    var duLieuCoSo =  x.stream().filter(item->item.getMaCoSo().equals(userInfo.getMaCoSo()));
//                                    BigDecimal doanhSoCoSo = BigDecimal.valueOf(0);
//
//                                    if(!duLieuCoSo.isParallel()){
//                                        doanhSoCoSo = duLieuCoSo.map(item-> item.getGiaBan().multiply(item.getSoLuong())).reduce(BigDecimal.ZERO, BigDecimal::add);
//                                    }
//                                    return new GiaoDichHangHoaRes(
//                                            x.get(0).getTenThuoc(),
//                                            x.get(0).getTenNhomThuoc(),
//                                            x.get(0).getTenDonVi(),
//                                            null,
//                                            null,
//                                            null,
//                                            x.stream().map(item->item.getGiaBan().multiply(item.getSoLuong())).reduce(BigDecimal.ZERO, BigDecimal::add),
//                                            doanhSoCoSo,
//                                            x.stream().map(GiaoDichHangHoa::getGiaBan).max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO),
//                                            x.stream().map(GiaoDichHangHoa::getGiaBan).min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO),
//                                            x.stream().map(GiaoDichHangHoa::getGiaNhap).max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO),
//                                            x.stream().map(GiaoDichHangHoa::getGiaNhap).min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO)
//                                    );
//                                }
//                        )
//                ))
//                .values().stream()
//                .sorted((g1, g2) -> g2.getSoLieuThiTruong().compareTo(g1.getSoLieuThiTruong()))
//                .limit(req.getPageSize())
//                .collect(Collectors.toList());

        return  null;
    }

    @Override
    public void pushData() {
        GiaoDichHangHoaReq req = new GiaoDichHangHoaReq();
        Calendar dateArchive = Calendar.getInstance();
        dateArchive.add(Calendar.MONDAY, -4);
        req.setFromDate(dateArchive.getTime());
        var giaoDichHangHoas = hdrRepo.searchList(req);

        redisListService.pushDataRedis(giaoDichHangHoas.stream().toList());
    }
}
