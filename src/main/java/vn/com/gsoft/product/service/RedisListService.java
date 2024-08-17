package vn.com.gsoft.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaData;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.model.dto.HangHoaRep;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface RedisListService {
    void pushDataRedis(List<GiaoDichHangHoa> giaoDichHangHoas);

    List<GiaoDichHangHoa> getTransactionsByDateAndThuocIds(HangHoaRep req, List<Long> thuocIds) throws ParseException;
}
