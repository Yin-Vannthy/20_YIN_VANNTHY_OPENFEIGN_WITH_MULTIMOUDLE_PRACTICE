package com.api.service;


import com.api.enums.Enums;
import com.api.model.dto.ProductDto;
import com.api.model.request.ProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductRequest productRequest);

    ProductDto getById(Long id);

    List<ProductDto> getAll(@Min(0) Integer pageNo, @Min(1) Integer pageSize, Enums.Product sortBy, Sort.Direction sortDirection);

    ProductDto updateById(Long id, @Valid ProductRequest productRequest);

    String deleteById(Long id);
}
