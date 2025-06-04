package com.example.demo.services;

import com.example.demo.dtos.CreatePizzaDTO;
import com.example.demo.dtos.PizzaDTO;
import com.example.demo.entities.IngredientEntity;
import com.example.demo.entities.PizzaEntity;
import com.example.demo.entities.PizzaIngredientEntity;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<PizzaDTO> getAllPizzas() {
        List<PizzaEntity> pizzas = pizzaRepository.findAll();

        return pizzas.stream()
                .map(pizza -> {
                    var ingredients = pizza.getPizzaIngredients()
                            .stream()
                            .map(pi -> pi.getIngredient().getName())
                            .toList();

                    return new PizzaDTO(
                            pizza.getId(),
                            pizza.getName(),
                            pizza.getPrice(),
                            ingredients
                    );
                })
                .toList();
    }

    public PizzaDTO createPizza(CreatePizzaDTO dto) {
        PizzaEntity pizza = new PizzaEntity();
        pizza.setName(dto.getName());
        pizza.setPrice(dto.getPrice());

        List<PizzaIngredientEntity> pizzaIngredients = new ArrayList<>();

        for (Integer ingredientId : dto.getIngredientIds()) {
            IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new RuntimeException("Ingrediente non trovato con id: " + ingredientId));

            PizzaIngredientEntity pizzaIngredient = new PizzaIngredientEntity();
            pizzaIngredient.setPizza(pizza);
            pizzaIngredient.setIngredient(ingredient);

            pizzaIngredients.add(pizzaIngredient);
        }

        pizza.setPizzaIngredients(pizzaIngredients);

        PizzaEntity savedPizza = pizzaRepository.save(pizza);

        List<String> ingredientNames = savedPizza.getPizzaIngredients().stream()
                .map(pi -> pi.getIngredient().getName())
                .toList();

        return new PizzaDTO(savedPizza.getId(), savedPizza.getName(), savedPizza.getPrice(), ingredientNames);
    }

}
