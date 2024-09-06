package com.api.service.serviceImpl;

import com.api.dto.ProductDto;
import com.api.enums.Enums;
import com.api.exception.CustomException;
import com.api.model.entity.Product;
import com.api.model.request.ProductRequest;
import com.api.repository.ProductRepository;
import com.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductDto save(ProductRequest productRequest) {
        return productRepository.save(productRequest.toEntity()).toProductResponse();
    }

    @Override
    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException("No Product with Id : " + id + " was found."))
                .toProductResponse();
    }

    @Override
    public List<ProductDto> getAll(Integer pageNo, Integer pageSize, Enums.Product sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy.name());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return productRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(Product::toProductResponse).toList();
    }

    @Override
    public ProductDto updateById(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(product -> productRequest.toEntity(product.getId()))
                .map(productRepository::save)
                .orElseThrow(() -> new CustomException("No Product with Id : " + id + " was found."))
                .toProductResponse();
    }

    @Override
    public String deleteById(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                product -> productRepository.deleteById(id),
                () -> { throw new CustomException("No Product with Id : " + id + " was found."); }
        );

        return "Product with Id : " + id + " was deleted successfully";
    }
}
