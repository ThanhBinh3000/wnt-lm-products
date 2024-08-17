package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.gsoft.product.model.system.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class PrivilegeReq extends BaseRequest {
    private String code;
    private String parentCode;
    private String name;
    private Boolean enable;
}
