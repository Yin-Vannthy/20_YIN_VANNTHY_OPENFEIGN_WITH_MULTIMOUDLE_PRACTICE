package com.api.service;


import com.api.enums.Enums;
import com.api.model.dto.CustomerDto;
import com.api.model.request.CustomerRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerRequest customerRequest);

    CustomerDto getById(Long id);

    List<CustomerDto> getAll(@Min(0) Integer pageNo, @Min(1) Integer pageSize, Enums.Customer sortBy, Sort.Direction sortDirection);

    CustomerDto updateById(Long id, @Valid CustomerRequest customerRequest);

    String deleteById(Long id);
}
