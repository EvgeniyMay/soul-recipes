package com.may.soulrecipes.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    @Length(min = 2, max = 50)
    private String title;

    private List<IngredientDTO> ingredients = new ArrayList<>();

    @Length(min = 10)
    private String instruction;

    @Length(min = 2, max = 200)
    private String description;

    private Long parentId;
}
