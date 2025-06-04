package com.example.demo.controllers;

import com.example.demo.dtos.*;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_ReturnsCreatedOrder() throws Exception {
        AdditionDTO addition = new AdditionDTO(1, null, 5, true);
        OrderItemDTO item = new OrderItemDTO(1, List.of(addition));
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of(item));

        OrderDTO createdOrder = new OrderDTO(1, "In attesa", LocalDateTime.now(), List.of(item));

        when(orderService.createOrder(org.mockito.ArgumentMatchers.any(CreateOrderDTO.class)))
                .thenReturn(createdOrder);


        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("In attesa"));
    }

    @Test
    void getOrders_ByStatusId_ReturnsCorrectList() throws Exception {
        OrderDTO order1 = new OrderDTO(1, "In attesa", LocalDateTime.now(), List.of());
        OrderDTO order2 = new OrderDTO(2, "In attesa", LocalDateTime.now(), List.of());

        when(orderService.getOrdersByStatusId(1)).thenReturn(List.of(order1, order2));

        mockMvc.perform(get("/order")
                        .param("statusId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void updateOrderStatus_ReturnsUpdatedOrder() throws Exception {
        Integer orderId = 1;
        Integer statusId = 2;
        OrderDTO updatedOrder = new OrderDTO(orderId, "In preparazione", LocalDateTime.now(), List.of());

        when(orderService.updateOrderStatus(orderId, statusId)).thenReturn(updatedOrder);

        mockMvc.perform(put("/order/{orderId}/status/{statusId}", orderId, statusId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.status").value("In preparazione"));
    }
}
