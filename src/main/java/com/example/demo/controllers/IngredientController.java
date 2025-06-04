package com.example.demo.controllers;

import com.example.demo.dtos.CreateIngredientDTO;
import com.example.demo.dtos.IngredientDTO;
import com.example.demo.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PostMapping
    public IngredientDTO createIngredient(@RequestBody CreateIngredientDTO dto) {
        return ingredientService.createIngredient(dto);
    }
}
