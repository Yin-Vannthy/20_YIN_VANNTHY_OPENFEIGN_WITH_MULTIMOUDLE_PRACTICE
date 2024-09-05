package com.api.service.serviceImpl;

import com.api.enums.Enums;
import com.api.exception.CustomException;
import com.api.model.dto.CustomerDto;
import com.api.model.entity.Customer;
import com.api.model.request.CustomerRequest;
import com.api.repository.CustomerRepository;
import com.api.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto save(CustomerRequest customerRequest) {
        if (customerRepository.findByEmail(customerRequest.getEmail()) != null) {
            throw new CustomException("Email already exist.Please use another email.");
        }
        return customerRepository.save(customerRequest.toEntity()).toCustomerResponse();
    }

    @Override
    public CustomerDto getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("No customer with Id : " + id + " was found."))
                .toCustomerResponse();
    }

    @Override
    public List<CustomerDto> getAll(Integer pageNo, Integer pageSize, Enums.Customer sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy.name());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return customerRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(Customer::toCustomerResponse).toList();
    }

    @Override
    public CustomerDto updateById(Long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findByEmail(customerRequest.getEmail());
        if (customer != null && !customer.getId().equals(id)) {
            throw new CustomException("Email already exist.Please use another email.");
        }

        return customerRepository.findById(id)
                .map(c -> customerRequest.toEntity(c.getId()))
                .map(customerRepository::save)
                .orElseThrow(() -> new CustomException("No customer with Id : " + id + " was found."))
                .toCustomerResponse();
    }

    @Override
    public String deleteById(Long id) {
        customerRepository.findById(id).ifPresentOrElse(
                customer -> customerRepository.deleteById(id),
                () -> { throw new CustomException("No customer with Id : " + id + " was found."); }
        );

        return "Customer with Id : " + id + " was deleted successfully";
    }
}
