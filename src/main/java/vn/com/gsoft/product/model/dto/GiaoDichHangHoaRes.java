package vn.com.gsoft.product.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.kafka.common.protocol.types.Field;
import vn.com.gsoft.product.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class GiaoDichHangHoaRes extends BaseRequest {
    private String tenThuoc;
    private String tenNhomThuoc;
    private String tenDonVi;
    private String tenDuocLy;
    private String tenNganhHang;
    private String tenHoatChat;

    private BigDecimal soLieuThiTruong;
    private BigDecimal soLieuCoSo;
    private BigDecimal giaBanMax;
    private BigDecimal giaBanMin;
    private BigDecimal giaNhapMax;
    private BigDecimal giaNhapMin;

    public GiaoDichHangHoaRes(String tenThuoc, String tenNhomThuoc, String tenDonVi,
                         String tenDuocLy, String tenNganhHang, String tenHoatChat,
                         BigDecimal soLieuThiTruong, BigDecimal soLieuCoSo,
                              BigDecimal giaBanMax, BigDecimal giaBanMin,
                              BigDecimal giaNhapMax, BigDecimal giaNhapMin

    ) {
        this.tenThuoc = tenThuoc;
        this.tenNhomThuoc = tenNhomThuoc;
        this.tenDonVi = tenDonVi;
        this.tenDuocLy = tenDuocLy;
        this.tenNganhHang = tenNganhHang;
        this.tenHoatChat = tenHoatChat;
        this.soLieuThiTruong = soLieuThiTruong;
        this.soLieuCoSo = soLieuCoSo;
        this.giaBanMax = giaBanMax;
        this.giaBanMin = giaBanMin;
        this.giaNhapMax = giaNhapMax;
        this.giaNhapMin = giaNhapMin;
    }
}
