package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.gsoft.product.model.system.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class TinhThanhsReq extends BaseRequest {
    private String maTinhThanh;
    private String tenTinhThanh;
}
