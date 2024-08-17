package vn.com.gsoft.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaData;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;

import java.util.List;
import java.util.Set;

public interface RedisListService {

    List<Object> getGiaoDichHangHoaValues(GiaoDichHangHoaReq rep);
    void pushDataRedis(List<GiaoDichHangHoa> giaoDichHangHoas);
    List<GiaoDichHangHoaData> getTransactionDetails(GiaoDichHangHoaReq req);
}
