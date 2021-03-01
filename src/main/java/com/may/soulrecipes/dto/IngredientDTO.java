package com.may.soulrecipes.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    private String name;

    private String measure;

    private Integer capacity;
}
