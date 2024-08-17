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
    private String tenDonVi;

    private BigDecimal soLieuThiTruong;
    private BigDecimal soLieuCoSo;
    private BigDecimal giaBanMax;
    private BigDecimal giaBanMin;
    private BigDecimal giaNhapMax;
    private BigDecimal giaNhapMin;

    public GiaoDichHangHoaRes(String tenDonVi,
                         BigDecimal soLieuThiTruong, BigDecimal soLieuCoSo,
                              BigDecimal giaBanMax, BigDecimal giaBanMin,
                              BigDecimal giaNhapMax, BigDecimal giaNhapMin

    ) {

        this.tenDonVi = tenDonVi;
        this.soLieuThiTruong = soLieuThiTruong;
        this.soLieuCoSo = soLieuCoSo;
        this.giaBanMax = giaBanMax;
        this.giaBanMin = giaBanMin;
        this.giaNhapMax = giaNhapMax;
        this.giaNhapMin = giaNhapMin;
    }
}
