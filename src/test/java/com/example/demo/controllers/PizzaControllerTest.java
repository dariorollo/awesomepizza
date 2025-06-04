package com.example.demo.controllers;

import com.example.demo.dtos.CreatePizzaDTO;
import com.example.demo.dtos.PizzaDTO;
import com.example.demo.services.PizzaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PizzaController.class)
class PizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PizzaService pizzaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPizzas_ReturnsCorrectList() throws Exception {
        List<PizzaDTO> mockPizzas = List.of(
                new PizzaDTO(1, "Margherita", BigDecimal.valueOf(8.50), List.of("Pomodoro", "Mozzarella", "Basilico")),
                new PizzaDTO(2, "Diavola", BigDecimal.valueOf(9.50), List.of("Pomodoro", "Mozzarella", "Salame piccante")),
                new PizzaDTO(3, "Quattro Formaggi", BigDecimal.valueOf(10.00), List.of("Mozzarella", "Gorgonzola", "Taleggio", "Parmigiano"))
        );

        when(pizzaService.getAllPizzas()).thenReturn(mockPizzas);

        mockMvc.perform(get("/pizzas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Margherita")))
                .andExpect(jsonPath("$[0].price", is(8.50)))
                .andExpect(jsonPath("$[0].ingredients", hasSize(3)))
                .andExpect(jsonPath("$[0].ingredients[0]", is("Pomodoro")))
                .andExpect(jsonPath("$[1].name", is("Diavola")))
                .andExpect(jsonPath("$[2].name", is("Quattro Formaggi")));
    }

    @Test
    void createPizza_ReturnsCreatedPizza() throws Exception {
        CreatePizzaDTO createDto = new CreatePizzaDTO("Capricciosa", BigDecimal.valueOf(9.00), List.of(2, 3, 8, 9));
        PizzaDTO createdPizzaDto = new PizzaDTO(4, "Capricciosa", BigDecimal.valueOf(9.00), List.of("Pomodoro", "Mozzarella", "Prosciutto", "Funghi"));

        when(pizzaService.createPizza(any(CreatePizzaDTO.class))).thenReturn(createdPizzaDto);

        mockMvc.perform(post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("Capricciosa")))
                .andExpect(jsonPath("$.price", is(9.00)))
                .andExpect(jsonPath("$.ingredients", hasSize(4)))
                .andExpect(jsonPath("$.ingredients[0]", is("Pomodoro")));
    }
}