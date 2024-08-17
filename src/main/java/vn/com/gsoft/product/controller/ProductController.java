package vn.com.gsoft.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.product.constant.PathConstant;
import vn.com.gsoft.product.model.dto.GiaoDichHangHoaReq;
import vn.com.gsoft.product.response.BaseResponse;
import vn.com.gsoft.product.service.GiaoDichHangHoaService;
import vn.com.gsoft.product.util.system.ResponseUtils;

@Slf4j
@RestController
@RequestMapping("/tra-cuu")
public class ProductController {
    @Autowired
    private GiaoDichHangHoaService service;

    @PostMapping(value = PathConstant.URL_SEARCH_PAGE +"-hang-hoa", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> searchPageHangHoa(@RequestBody GiaoDichHangHoaReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.searchPageHangHoa(objReq)));
    }
}
