package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionDTO {
    private Integer id;
    private Integer orderId;
    private Integer ingredientId;
    private Boolean isAddition; // true aggiunta, false rimozione
}
