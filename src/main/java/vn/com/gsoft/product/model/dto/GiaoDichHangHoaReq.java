package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.gsoft.product.model.system.BaseRequest;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GiaoDichHangHoaReq extends BaseRequest {
    private Boolean dongBang;
    private String maCoSo;
    private Integer nhomThuocId;
    private Integer nhomDuocLyId;
    private Integer nhomNganhHangId;
    private Integer thuocId;
    private Integer nhomHoatChatId;
    private Integer hangThayTheId;
    private List<Long> thuocids;
}
