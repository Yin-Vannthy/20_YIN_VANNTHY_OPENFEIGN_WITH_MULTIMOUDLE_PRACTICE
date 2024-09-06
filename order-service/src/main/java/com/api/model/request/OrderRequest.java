package com.api.model.request;

import com.api.model.entity.Order;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    @NotNull(message = "Customer Id Can not be null")
    @Min(value = 1, message = "Customer Id must be greater than 0")
    private Long customerId;

    @NotNull(message = "Product Id Can not be null")
    private List<Long> productIds;

    @NotNull(message = "Order Date Can not be null")
    private LocalDate orderDate = LocalDate.now();

    public Order toEntity() {
        return new Order(null, customerId, productIds, orderDate);
    }

    public Order toEntity(Long id) {return new Order(id, customerId, productIds, orderDate);}
}
