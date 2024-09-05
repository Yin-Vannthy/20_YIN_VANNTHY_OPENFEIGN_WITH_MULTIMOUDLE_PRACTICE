package com.api.configuration;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceFeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ServiceErrorDecoder();
    }
}
