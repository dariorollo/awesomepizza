package com.example.demo.repositories;

import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByStatus(OrderStatusEntity status);
}
