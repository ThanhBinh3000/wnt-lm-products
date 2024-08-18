package vn.com.gsoft.product.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HangHoaRes {
    private Integer thuocId;
    private String tenThuoc;
    private String tenNhomThuoc;
    private String tenDonVi;
}
