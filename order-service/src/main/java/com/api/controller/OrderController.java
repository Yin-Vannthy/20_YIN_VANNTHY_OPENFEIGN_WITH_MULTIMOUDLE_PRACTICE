package com.api.controller;

import com.api.enums.Enums;
import com.api.model.dto.OrderDto;
import com.api.model.request.OrderRequest;
import com.api.response.ApiResponse;
import com.api.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<OrderDto>> save(@Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.save(orderRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.getById(id),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAll(
            @RequestParam(defaultValue = "0", required = false) @Min(0)  Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) @Min(1)  Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) Enums.Order sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.getAll(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.deleteById(id),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> updateById(@PathVariable @Min(1) Long id, @Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.updateById(id, orderRequest),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<ApiResponse<String>> deleteByCustomerId(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.deleteByCustomerId(id),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse<String>> deleteByProductId(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        orderService.deleteByProductId(id),
                        HttpStatus.OK
                )
        );
    }
}
