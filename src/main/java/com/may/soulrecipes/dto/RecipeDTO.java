package com.may.soulrecipes.dto;

import com.may.soulrecipes.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class RecipeDTO {

    private String title;

    private List<IngredientDTO> ingredients = new ArrayList<>();

    private String instruction;

    private String description;

    private Long parentId;
}
