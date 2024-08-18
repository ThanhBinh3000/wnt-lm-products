package vn.com.gsoft.product.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.entity.HangHoa;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.HangHoaRep;
import vn.com.gsoft.product.model.dto.HangHoaRes;

import java.util.List;

public interface HangHoaService extends BaseService<HangHoa, HangHoaRep, Long> {
    Page<HangHoa> searchPageHangHoa(HangHoaRep req) throws Exception;

    void pushTransactionData();

    void pushProductData();

    List<HangHoaRes> getProductData();
}