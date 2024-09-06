package com.api.service;


import com.api.enums.Enums;
import com.api.model.dto.OrderDto;
import com.api.model.request.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderRequest orderRequest);

    OrderDto getById(Long id);

    List<OrderDto> getAll(Integer pageNo, Integer pageSize, Enums.Order sortBy, Sort.Direction sortDirection);

    String deleteById(Long id);

    OrderDto updateById(Long id, @Valid OrderRequest orderRequest);
}
