package com.may.soulrecipes.service;

import com.may.soulrecipes.repository.IngredientCapacityRepository;
import com.may.soulrecipes.repository.IngredientRepository;
import com.may.soulrecipes.repository.InstructionRepository;
import com.may.soulrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecipeService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCapacityRepository ingredientCapacityRepository;
    private final InstructionRepository instructionRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(IngredientRepository ingredientRepository,
                         IngredientCapacityRepository ingredientCapacityRepository,
                         InstructionRepository instructionRepository,
                         RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCapacityRepository = ingredientCapacityRepository;
        this.instructionRepository = instructionRepository;
        this.recipeRepository = recipeRepository;
    }

}
