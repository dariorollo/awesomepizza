package com.example.demo.controllers;

import com.example.demo.dtos.OrderStatusDTO;
import com.example.demo.services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-statuses")
public class OrderStatusController {

    @Autowired
    private OrderStatusService orderStatusService;

    @GetMapping
    public List<OrderStatusDTO> getAllOrderStatuses() {
        return orderStatusService.getAllStatuses();
    }
}
