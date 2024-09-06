package com.api.service;


import com.api.dto.ProductDto;
import com.api.enums.Enums;
import com.api.model.request.ProductRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductRequest productRequest);

    ProductDto getById(Long id);

    List<ProductDto> getAll(Integer pageNo,Integer pageSize, Enums.Product sortBy, Sort.Direction sortDirection);

    ProductDto updateById(Long id,ProductRequest productRequest);

    String deleteById(Long id);
}
