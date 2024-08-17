package vn.com.gsoft.product.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.gsoft.product.response.BaseResponse;

@FeignClient(name = "wnt-lm-security")
public interface UserProfileFeign {
    @GetMapping("/profile")
    BaseResponse getProfile();
}
