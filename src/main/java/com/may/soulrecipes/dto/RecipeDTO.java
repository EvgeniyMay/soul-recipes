package com.may.soulrecipes.dto;

import com.may.soulrecipes.entity.IngredientCapacity;
import com.may.soulrecipes.entity.Instruction;
import com.may.soulrecipes.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RecipeDTO {

    private String title;

    private List<IngredientCapacity> ingredientCapacities;

    private Instruction instruction;

    private String description;

    private Recipe parent;
}
