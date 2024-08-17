package vn.com.gsoft.product.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.com.gsoft.product.service.HangHoaService;

@SpringBootTest
@Slf4j
class PushDataRedis {
    @Autowired
    private HangHoaService giaoDichHangHoaService;
    @Test
    void searchPageCustom() throws Exception {
        giaoDichHangHoaService.pushData();
    }
}