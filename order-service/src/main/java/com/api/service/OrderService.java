package com.api.service;


import com.api.enums.Enums;
import com.api.model.dto.OrderDto;
import com.api.model.request.OrderRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderRequest orderRequest);

    OrderDto getById(@Min(1) Long id);

    List<OrderDto> getAll(@Min(0) Integer pageNo, @Min(1) Integer pageSize, Enums.Order sortBy, Sort.Direction sortDirection);

    String deleteById(@Min(1) Long id);

    OrderDto updateById(@Min(1) Long id, @Valid OrderRequest orderRequest);
}
