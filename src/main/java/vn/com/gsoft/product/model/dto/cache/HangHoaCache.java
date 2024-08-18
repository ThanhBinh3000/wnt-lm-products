package vn.com.gsoft.product.model.dto.cache;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HangHoaCache {
    private Integer thuocId;
    private String tenThuoc;
    private String tenNhomThuoc;
    private String tenDonVi;
}
