package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.gsoft.product.model.system.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class GiaoDichHangHoaReq extends BaseRequest {
    private Boolean dongBang;
    private Integer LoaiGiaoDich;
    private String maCoSo;
    private Integer loaiBaoCao;
    private Integer pageSize;
}
