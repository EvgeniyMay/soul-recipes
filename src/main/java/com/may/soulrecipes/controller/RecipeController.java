package com.may.soulrecipes.controller;

import com.may.soulrecipes.dto.IngredientDTO;
import com.may.soulrecipes.dto.RecipeDTO;
import com.may.soulrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/create")
    public String getCreateRecipePage(Model model,
                                      @ModelAttribute("recipeForm")
                                              RecipeDTO recipeForm,
                                      @RequestParam(value = "ingredientSize", required = false)
                                              Integer ingredientSize) {
        // ToDo | Refactor
        // User value or 1
        ingredientSize = ingredientSize == null ? 1 : ingredientSize;
        // Create if not exists
        recipeForm = recipeForm == null ? new RecipeDTO() : recipeForm;

        // ToDo | Refactor
        for(int i=0; i<ingredientSize; ++i){
            recipeForm.getIngredients().add(new IngredientDTO());
        }

        model.addAttribute("recipeForm", recipeForm);
        model.addAttribute("allRecipes", recipeService.getAll());

        return "recipe/createRecipe";
    }

    @PostMapping("/create")
    public String createRecipe(@ModelAttribute("recipeForm")
                                           RecipeDTO recipeForm){
        try {
            recipeService.create(recipeForm);
        } catch (Exception e) {
            // ToDo | Add fail creation info
            e.printStackTrace();
        }

        return "redirect:/all";
    }
}
