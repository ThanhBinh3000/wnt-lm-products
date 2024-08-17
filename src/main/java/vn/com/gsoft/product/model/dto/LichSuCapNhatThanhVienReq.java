package vn.com.gsoft.product.model.dto;

import lombok.Data;

import java.util.Date;
@Data
public class LichSuCapNhatThanhVienReq {
    private Long id;
    private Date ngayCapNhat;
    private String ghiChu;
    private String maThanhVien;
    private Integer statusId;
}
