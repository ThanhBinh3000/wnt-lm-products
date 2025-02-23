package vn.com.gsoft.product.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HangHoa")
public class HangHoa extends BaseEntity {
    @Id
    @Column(name = "Id")
    private Long id ;
    @Column(name = "ThuocId")
    private Long thuocId;
    @Column(name = "TenThuoc")
    private String tenThuoc;
    @Column(name = "NhomThuocId")
    private Integer nhomThuocId;
    @Column(name = "TenNhomThuoc")
    private String tenNhomThuoc;
    @Column(name = "NhomDuocLyId")
    private Integer nhomDuocLyId;
    @Column(name = "TenNhomDuocLy")
    private String tenNhomDuocLy;
    @Column(name = "NhomHoatChatId")
    private Integer nhomHoatChatId;
    @Column(name = "TenNhomHoatChat")
    private String tenNhomHoatChat;
    @Column(name = "NhomNganhHangId")
    private Integer nhomNganhHangId;
    @Column(name = "TenNhomNganhHang")
    private String tenNhomNganhHang;
    @Column(name = "TenDonVi")
    private String tenDonVi;
    @Transient
    private BigDecimal giaBanMax;
    @Transient
    private BigDecimal giaBanMin;
    @Transient
    private BigDecimal giaNhapMax;
    @Transient
    private BigDecimal giaNhapMin;
    @Transient
    private BigDecimal doanhSoTT;
    @Transient
    private BigDecimal doanhSoCS;
}
