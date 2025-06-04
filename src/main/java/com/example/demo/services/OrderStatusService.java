package com.example.demo.services;

import com.example.demo.dtos.OrderStatusDTO;
import com.example.demo.entities.OrderStatusEntity;
import com.example.demo.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusService {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public List<OrderStatusDTO> getAllStatuses() {
        return orderStatusRepository.findAll().stream()
                .map(status -> new OrderStatusDTO(status.getId(), status.getName()))
                .collect(Collectors.toList());
    }
}
