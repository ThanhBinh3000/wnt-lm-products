package vn.com.gsoft.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaRes;

import java.util.List;

public interface GiaoDichHangHoaService extends BaseService<GiaoDichHangHoa, GiaoDichHangHoaReq, Long> {
    List<GiaoDichHangHoaRes> searchPageHangHoa(GiaoDichHangHoaReq req) throws Exception;
    void pushData();
}