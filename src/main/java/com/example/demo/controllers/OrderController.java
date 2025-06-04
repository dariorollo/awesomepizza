package com.example.demo.controllers;

import com.example.demo.dtos.CreateOrderDTO;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dtos.OrderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @PostMapping
   public OrderDTO createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
       return orderService.createOrder(createOrderDTO);
   }

   @GetMapping
   public List<OrderDTO> getOrders(@RequestParam(required = false) Integer statusId) {
       return orderService.getOrdersByStatusId(statusId);
   }

   @PutMapping("/{orderId}/status/{statusId}")
   public OrderDTO updateOrderStatus(
           @PathVariable Integer orderId,
           @PathVariable Integer statusId) {
       return orderService.updateOrderStatus(orderId, statusId);
    }
}
