package com.api.feignclient;

import com.api.configuration.ServiceFeignConfig;
import com.api.dto.CustomerDto;
import com.api.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service",
        url = "http://localhost:8081/api/v1/customers",
        configuration = ServiceFeignConfig.class)
public interface CustomerClient {
    @GetMapping("/{id}")
    ApiResponse<CustomerDto> getById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable Long id);
}
