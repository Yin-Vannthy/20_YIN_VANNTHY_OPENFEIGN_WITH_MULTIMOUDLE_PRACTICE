package com.api.feignclient;

import com.api.configuration.ServiceFeignConfig;
import com.api.dto.ProductDto;
import com.api.response.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",
        url = "http://localhost:8082/api/v1/products",
        configuration = ServiceFeignConfig.class)
public interface ProductClient {
    @GetMapping("/{id}")
    ApiResponse<ProductDto> getById(@PathVariable @Min(1) Long id);
}
