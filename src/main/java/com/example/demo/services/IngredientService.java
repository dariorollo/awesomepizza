package com.example.demo.services;

import com.example.demo.dtos.CreateIngredientDTO;
import com.example.demo.dtos.IngredientDTO;
import com.example.demo.entities.IngredientEntity;
import com.example.demo.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientDTO> getAllIngredients() {
        List<IngredientEntity> ingredients = ingredientRepository.findAll();

        return ingredients.stream()
                .map(i -> new IngredientDTO(i.getId(), i.getName(), i.getDescription(), i.getPrice()))
                .toList();
    }

    public IngredientDTO createIngredient(CreateIngredientDTO dto) {
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName(dto.getName());
        ingredient.setDescription(dto.getDescription());
        ingredient.setPrice(dto.getPrice());

        IngredientEntity saved = ingredientRepository.save(ingredient);

        return new IngredientDTO(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice()
        );
    }
}
