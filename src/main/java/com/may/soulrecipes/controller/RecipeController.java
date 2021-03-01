package com.may.soulrecipes.controller;

import com.may.soulrecipes.dto.IngredientDTO;
import com.may.soulrecipes.dto.RecipeDTO;
import com.may.soulrecipes.entity.Recipe;
import com.may.soulrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/all")
    public String getAllRecipesPage(Model model,
                                    @PageableDefault(sort = {"title"}, size = 10)
                                    Pageable pageable) {
        model.addAttribute("recipePage", recipeService.getPageable(pageable));

        return "recipe/allRecipes";
    }

    @GetMapping("/create")
    public String getCreateRecipePage(Model model,
                                      @ModelAttribute("recipeForm")
                                              RecipeDTO recipeForm,
                                      @RequestParam(value = "ingredientSize", required = false)
                                              Integer ingredientSize) {
        // ToDo | Add validation

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

    @GetMapping("/{recipeId}/details")
    public String getRecipeDetailsPage(Model model,
                                       @PathVariable Long recipeId) {
        Recipe recipe = recipeService.getById(recipeId);
        //ToDo | Refactor
        List<String> instruction = Arrays.asList(recipe.getInstruction()
                .getText().split(System.lineSeparator()));

        model.addAttribute("recipe", recipe);
        model.addAttribute("instruction", instruction);

        return "recipe/recipeDetails";
    }

    @GetMapping("/{recipeId}/edit")
    public String getEditRecipePage(Model model,
                                    @PathVariable Long recipeId) {
        model.addAttribute("recipeForm", recipeService.getDtoById(recipeId));
        model.addAttribute("allRecipes",
                recipeService.getAll().stream()
                        .filter(r -> !Objects.equals(r.getId(), recipeId))
                        .collect(Collectors.toList()));

        return "recipe/editRecipe";
    }

    @PostMapping("/edit")
    public String editRecipe(@ModelAttribute("recipeForm")
                                         RecipeDTO recipeForm) {
        // ToDo | Add validation
        try {
            recipeService.update(recipeForm);
        } catch (Exception e) {
            // ToDo | Add fail creation info
            e.printStackTrace();
        }

        return "redirect:/all";
    }
}
