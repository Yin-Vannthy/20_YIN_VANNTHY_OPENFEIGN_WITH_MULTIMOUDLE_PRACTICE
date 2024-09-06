package com.api.feignclient;

import com.api.configuration.ServiceFeignConfig;
import com.api.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service",
        url = "http://localhost:8083/api/v1/orders",
        configuration = ServiceFeignConfig.class)
public interface OrderClient {
    @DeleteMapping("/customer/{id}")
    ApiResponse<String> deleteByCustomerId(@PathVariable Long id);

    @DeleteMapping("/product/{id}")
    ApiResponse<String> deleteByProductId(@PathVariable Long id);
}
