package com.api.model.entity;

import com.api.dto.CustomerDto;
import com.api.dto.ProductDto;
import com.api.model.dto.OrderDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "order_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;

    @ElementCollection
    private List<Long> productIds;
    private LocalDate orderDate;

    public OrderDto toOrderResponse(CustomerDto customer, Set<ProductDto> products) {
        return new OrderDto(this.id, customer, products, orderDate);
    }
}
