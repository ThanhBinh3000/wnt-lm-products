package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.gsoft.product.model.system.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolePrivilegeReq extends BaseRequest {

    private Long roleId;

    private Long privilegeId;
}
