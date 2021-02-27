package com.may.soulrecipes.controller;

import com.may.soulrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/all")
    public String getAllRecipesPage(Model model) {
        model.addAttribute("recipes", recipeService.getAll());

        return "recipe/allRecipes";
    }
}
