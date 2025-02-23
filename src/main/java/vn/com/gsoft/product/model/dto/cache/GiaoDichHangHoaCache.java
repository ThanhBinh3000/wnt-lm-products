package vn.com.gsoft.product.model.dto.cache;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GiaoDichHangHoaCache {
    private Long id ;
    private Long thuocId;
    private String tenThuoc;
    private Integer nhomThuocId;
    private String tenNhomThuoc;
    private Integer nhomDuocLyId;
    private String tenNhomDuocLy;
    private BigDecimal soLuong;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private String tenDonVi;
    private String tenHoatChat;
    private Date ngayGiaoDich;
    private Boolean dongBang;
    private Integer LoaiGiaoDich;
    private String maCoSo;
    private BigDecimal soLuongQuyDoi;
}
