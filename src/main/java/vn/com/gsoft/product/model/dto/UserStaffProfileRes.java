package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStaffProfileRes {
    private Long id;
    private String userName;
    private String soDienThoai;
    private String role;
    private String email;
    private Boolean HoatDong;
    private String tenDayDu;
}
