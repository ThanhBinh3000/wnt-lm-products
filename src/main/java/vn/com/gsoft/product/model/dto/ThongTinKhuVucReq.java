package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.gsoft.product.model.system.BaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class ThongTinKhuVucReq extends BaseRequest {
    private Long id;
    private Long cityId;
    private Long wardId;
    private Long regionId;
}
