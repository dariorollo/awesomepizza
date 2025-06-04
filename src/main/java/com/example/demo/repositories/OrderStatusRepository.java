package com.example.demo.repositories;

import com.example.demo.entities.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Integer> {
}
