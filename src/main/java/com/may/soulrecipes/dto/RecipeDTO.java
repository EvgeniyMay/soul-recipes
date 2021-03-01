package com.may.soulrecipes.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    private String title;

    private List<IngredientDTO> ingredients = new ArrayList<>();

    private String instruction;

    private String description;

    private Long parentId;
}
