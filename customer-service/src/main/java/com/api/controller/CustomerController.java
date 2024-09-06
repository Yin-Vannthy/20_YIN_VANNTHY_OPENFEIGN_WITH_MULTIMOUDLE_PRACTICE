package com.api.controller;

import com.api.dto.CustomerDto;
import com.api.enums.Enums;
import com.api.model.request.CustomerRequest;
import com.api.response.ApiResponse;
import com.api.service.CustomerService;
import com.api.util.APIResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<CustomerDto>> save(@Valid @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        customerService.save(customerRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> getById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        customerService.getById(id),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAll(
            @RequestParam(defaultValue = "0", required = false) @Min(0)  Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) @Min(1)  Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) Enums.Customer sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        customerService.getAll(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> updateById(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        customerService.updateById(id, customerRequest),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        customerService.deleteById(id),
                        HttpStatus.OK
                )
        );
    }
}
