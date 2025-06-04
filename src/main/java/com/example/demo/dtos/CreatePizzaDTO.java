package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePizzaDTO {
    private String name;
    private BigDecimal price;
    private List<Integer> ingredientIds;
}
