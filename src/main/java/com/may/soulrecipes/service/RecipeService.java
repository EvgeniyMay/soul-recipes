package com.may.soulrecipes.service;

import com.may.soulrecipes.entity.Recipe;
import com.may.soulrecipes.repository.IngredientCapacityRepository;
import com.may.soulrecipes.repository.IngredientRepository;
import com.may.soulrecipes.repository.InstructionRepository;
import com.may.soulrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

}
