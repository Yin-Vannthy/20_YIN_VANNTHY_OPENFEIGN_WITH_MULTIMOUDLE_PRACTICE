package com.api.model.dto;

import com.api.dto.CustomerDto;
import com.api.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private CustomerDto customerId;
    private Set<ProductDto> productIds;
    private LocalDate orderDate;
}
