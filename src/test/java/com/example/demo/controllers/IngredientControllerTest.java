package com.example.demo.controllers;

import com.example.demo.dtos.CreateIngredientDTO;
import com.example.demo.dtos.IngredientDTO;
import com.example.demo.services.IngredientService;
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

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllIngredients_ReturnsCorrectList() throws Exception {
        List<IngredientDTO> mockIngredients = List.of(
                new IngredientDTO(1, "Mozzarella", "Mozzarella fresca di latte vaccino", BigDecimal.valueOf(1.50)),
                new IngredientDTO(2, "Salsa di Pomodoro", "Salsa di pomodoro fatta in casa", BigDecimal.valueOf(0.75)),
                new IngredientDTO(3, "Basilico", "Foglie fresche di basilico", BigDecimal.valueOf(0.20))
        );

        when(ingredientService.getAllIngredients()).thenReturn(mockIngredients);

        mockMvc.perform(get("/ingredients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Mozzarella")))
                .andExpect(jsonPath("$[0].description", is("Mozzarella fresca di latte vaccino")))
                .andExpect(jsonPath("$[0].price", is(1.50)))
                .andExpect(jsonPath("$[1].name", is("Salsa di Pomodoro")))
                .andExpect(jsonPath("$[2].name", is("Basilico")));
    }

    @Test
    void createIngredient_ReturnsCreatedIngredient() throws Exception {
        CreateIngredientDTO createDto = new CreateIngredientDTO("Salame Piccante", "Salame stagionato piccante", BigDecimal.valueOf(2.00));

        IngredientDTO createdIngredientDto = new IngredientDTO(4, "Salame Piccante", "Salame stagionato piccante", BigDecimal.valueOf(2.00));

        when(ingredientService.createIngredient(any(CreateIngredientDTO.class))).thenReturn(createdIngredientDto);

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("Salame Piccante")))
                .andExpect(jsonPath("$.description", is("Salame stagionato piccante")))
                .andExpect(jsonPath("$.price", is(2.00)));
    }
}