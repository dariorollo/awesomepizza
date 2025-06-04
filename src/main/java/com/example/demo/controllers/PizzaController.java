package com.example.demo.controllers;

import com.example.demo.dtos.CreatePizzaDTO;
import com.example.demo.services.PizzaService;
import com.example.demo.dtos.PizzaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<PizzaDTO> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @PostMapping
    public ResponseEntity<PizzaDTO> createPizza(@RequestBody CreatePizzaDTO dto) {
        PizzaDTO createdPizza = pizzaService.createPizza(dto);
        return new ResponseEntity<>(createdPizza, HttpStatus.CREATED);
    }
}
