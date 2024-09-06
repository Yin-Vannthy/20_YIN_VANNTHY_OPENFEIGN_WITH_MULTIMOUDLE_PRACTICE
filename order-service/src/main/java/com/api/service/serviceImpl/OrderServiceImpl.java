package com.api.service.serviceImpl;

import com.api.dto.CustomerDto;
import com.api.dto.ProductDto;
import com.api.enums.Enums;
import com.api.exception.CustomException;
import com.api.feignclient.CustomerClient;
import com.api.feignclient.ProductClient;
import com.api.model.dto.OrderDto;
import com.api.model.entity.Order;
import com.api.model.request.OrderRequest;
import com.api.repository.OrderRepository;
import com.api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private CustomerDto getCustomerById(Long id) {
        return customerClient.getById(id).getPayload();
    }

    private ProductDto getProductById(Long id) {
        return productClient.getById(id).getPayload();
    }

    @Override
    public OrderDto save(OrderRequest orderRequest) {
        CustomerDto customer = getCustomerById(orderRequest.getCustomerId());
        Set<ProductDto> products = orderRequest.getProductIds()
                .stream()
                .map(this::getProductById)
                .collect(Collectors.toSet());

        return orderRepository
                .save(orderRequest.toEntity())
                .toOrderResponse(customer, products);
    }

    @Override
    public OrderDto getById(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    CustomerDto customer = getCustomerById(order.getCustomerId());
                    Set<ProductDto> products = order.getProductIds()
                            .stream()
                            .map(this::getProductById)
                            .collect(Collectors.toSet());
                    return order.toOrderResponse(customer, products);
                })
                .orElseThrow(() -> new CustomException("No order with Id : " + id + " was found."));
    }

    @Override
    public List<OrderDto> getAll(Integer pageNo, Integer pageSize, Enums.Order sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy.name());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return orderRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(order -> {
                            CustomerDto customer = getCustomerById(order.getCustomerId());
                            Set<ProductDto> products = order.getProductIds()
                                    .stream()
                                    .map(this::getProductById)
                                    .collect(Collectors.toSet());
                            return order.toOrderResponse(customer, products);
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public String deleteById(Long id) {
        orderRepository.findById(id)
                .ifPresentOrElse(
                        order -> orderRepository.deleteById(id),
                        () -> {
                            throw new CustomException("No order with Id : " + id + " was found.");
                        }
                );

        return "Order with Id : " + id + " was deleted successfully";
    }

    @Override
    public OrderDto updateById(Long id, OrderRequest orderRequest) {
        return orderRepository.findById(id)
                .map(order -> {
                    CustomerDto customer = getCustomerById(orderRequest.getCustomerId());
                    Set<ProductDto> products = orderRequest.getProductIds()
                            .stream()
                            .map(this::getProductById)
                            .collect(Collectors.toSet());

                    return orderRepository
                            .save(orderRequest.toEntity(order.getId()))
                            .toOrderResponse(customer, products);
                })
                .orElseThrow(() -> new CustomException("No order with Id : " + id + " was found."));
    }

    @Transactional
    @Override
    public String deleteByCustomerId(Long id) {
        orderRepository.findByCustomerId(id)
                .ifPresentOrElse(
                        order -> orderRepository.deleteOrdersByCustomerId(id),
                        () -> {throw new CustomException("No order with customer Id : " + id + " was found.");}
                );

        return "Order with customer Id : " + id + " was deleted successfully";
    }

    @Override
    public String deleteByProductId(Long productId) {
        List<Order> orders = orderRepository.findByProductIdsContaining(productId)
                .orElseThrow(() -> new CustomException("No order with product Id : " + productId + " was found."));

        orders.forEach(order -> {
            List<Long> productIds = order.getProductIds()
                    .stream()
                    .filter(id -> !id.equals(productId))
                    .collect(Collectors.toList());

            if (productIds.isEmpty()) {
                orderRepository.delete(order);
            } else {
                order.setProductIds(productIds);
                orderRepository.save(order);
            }
        });

        return "Order with product Id : " + productId + " was deleted successfully";
    }
}
