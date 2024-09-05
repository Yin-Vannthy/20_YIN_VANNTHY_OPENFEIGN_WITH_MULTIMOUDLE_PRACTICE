package com.api.controller;

import com.api.enums.Enums;
import com.api.model.dto.ProductDto;
import com.api.model.request.ProductRequest;
import com.api.response.ApiResponse;
import com.api.service.ProductService;
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
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<ProductDto>> save(@Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        productService.save(productRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        productService.getById(id),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAll(
            @RequestParam(defaultValue = "0", required = false) @Min(0)  Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) @Min(1)  Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) Enums.Product sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        productService.getAll(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateById(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        productService.updateById(id, productRequest),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        productService.deleteById(id),
                        HttpStatus.OK
                )
        );
    }
}
