package vn.com.gsoft.product.service;

import vn.com.gsoft.product.entity.GiaoDichHangHoa;
import vn.com.gsoft.product.model.dto.HangHoaRep;
import vn.com.gsoft.product.model.dto.cache.HangHoaCache;

import java.text.ParseException;
import java.util.List;

public interface RedisListService {
    void pushTransactionDataRedis(List<GiaoDichHangHoa> giaoDichHangHoas);

    List<GiaoDichHangHoa> getTransactionsByDateAndThuocIds(HangHoaRep req, List<Long> thuocIds) throws ParseException;
}
