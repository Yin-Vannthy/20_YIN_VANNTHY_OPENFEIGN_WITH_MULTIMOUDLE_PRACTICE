package com.api.service;


import com.api.dto.CustomerDto;
import com.api.enums.Enums;
import com.api.model.request.CustomerRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerRequest customerRequest);

    CustomerDto getById(Long id);

    List<CustomerDto> getAll(Integer pageNo,Integer pageSize, Enums.Customer sortBy, Sort.Direction sortDirection);

    CustomerDto updateById(Long id,CustomerRequest customerRequest);

    String deleteById(Long id);
}
