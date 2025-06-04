package com.example.demo.controllers;

import com.example.demo.dtos.OrderStatusDTO;
import com.example.demo.services.OrderStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderStatusController.class)
class OrderStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderStatusService orderStatusService;

    @Test
    void getAllOrderStatuses_ReturnsCorrectList() throws Exception {
        List<OrderStatusDTO> mockStatuses = List.of(
                new OrderStatusDTO(1, "In attesa"),
                new OrderStatusDTO(2, "In preparazione"),
                new OrderStatusDTO(3, "Pronto")
        );

        when(orderStatusService.getAllStatuses()).thenReturn(mockStatuses);

        mockMvc.perform(get("/order-statuses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("In attesa")))
                .andExpect(jsonPath("$[1].name", is("In preparazione")))
                .andExpect(jsonPath("$[2].name", is("Pronto")));
    }
}
