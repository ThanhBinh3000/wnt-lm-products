package vn.com.gsoft.product.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.entity.HangHoa;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.HangHoaRep;

public interface HangHoaService extends BaseService<HangHoa, HangHoaRep, Long> {
    Page<HangHoa> searchPageHangHoa(HangHoaRep req) throws Exception;
    void pushData();
}