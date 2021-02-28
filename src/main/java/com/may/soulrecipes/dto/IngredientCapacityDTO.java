package com.may.soulrecipes.dto;

import com.may.soulrecipes.entity.Ingredient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientCapacityDTO {

    private Ingredient ingredient;

    private String measure;

    private Integer capacity;
}
