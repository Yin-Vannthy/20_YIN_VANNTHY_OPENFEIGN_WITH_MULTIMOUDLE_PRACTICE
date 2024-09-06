package com.api.repository;

import com.api.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteOrdersByCustomerId(Long customerId);

    Optional<Order> findByCustomerId(Long customerId);

    Optional<List<Order>> findByProductIdsContaining(Long productId);
}
