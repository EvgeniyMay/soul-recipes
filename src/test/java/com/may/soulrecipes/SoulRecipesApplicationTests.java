package com.may.soulrecipes;

import com.may.soulrecipes.controller.RecipeController;
import com.may.soulrecipes.repository.IngredientCapacityRepository;
import com.may.soulrecipes.repository.IngredientRepository;
import com.may.soulrecipes.repository.InstructionRepository;
import com.may.soulrecipes.repository.RecipeRepository;
import com.may.soulrecipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SoulRecipesApplicationTests {

    @Autowired
    private RecipeController recipeController;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private InstructionRepository instructionRepository;

    @Autowired
    private IngredientCapacityRepository ingredientCapacityRepository;

    @Autowired
    private IngredientRepository ingredientRepository;



    @Test
    void recipeControllerLoads() {
        assertThat(recipeController).isNotNull();
    }

    @Test
    void recipeServiceLoads() {
        assertThat(recipeService).isNotNull();
    }

    @Test
    void recipeRepositoryLoads() {
        assertThat(recipeRepository).isNotNull();
    }

    @Test
    void instructionRepositoryLoads() {
        assertThat(instructionRepository).isNotNull();
    }

    @Test
    void ingredientCapacityRepositoryLoads() {
        assertThat(ingredientCapacityRepository).isNotNull();
    }

    @Test
    void ingredientRepositoryLoads() {
        assertThat(ingredientRepository).isNotNull();
    }
}
