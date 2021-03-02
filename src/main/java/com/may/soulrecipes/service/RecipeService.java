package com.may.soulrecipes.service;

import com.may.soulrecipes.dto.IngredientDTO;
import com.may.soulrecipes.dto.RecipeDTO;
import com.may.soulrecipes.entity.Ingredient;
import com.may.soulrecipes.entity.IngredientCapacity;
import com.may.soulrecipes.entity.Instruction;
import com.may.soulrecipes.entity.Recipe;
import com.may.soulrecipes.exception.*;
import com.may.soulrecipes.repository.IngredientCapacityRepository;
import com.may.soulrecipes.repository.IngredientRepository;
import com.may.soulrecipes.repository.InstructionRepository;
import com.may.soulrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final InstructionRepository instructionRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientCapacityRepository ingredientCapacityRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         InstructionRepository instructionRepository,
                         IngredientRepository ingredientRepository,
                         IngredientCapacityRepository ingredientCapacityRepository) {
        this.recipeRepository = recipeRepository;
        this.instructionRepository = instructionRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientCapacityRepository = ingredientCapacityRepository;
    }


    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public Page<Recipe> getPageable(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(()-> new RecipeNotFountException(
                        "Recipe with id " + id + " not found"));
    }

    @Transactional
    public Recipe update(RecipeDTO recipeDTO) {
        Recipe recipe = Recipe.builder()
                .id(recipeDTO.getId())
                .title(recipeDTO.getTitle())
                .description(recipeDTO.getDescription())
                .ingredientCapacities(getIngredientCapacities(recipeDTO))
                .parent(getParent(recipeDTO))
                .updateDate(LocalDateTime.now())
                .build();

        updateInstruction(recipeDTO);

        try {
            return recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new UpdatingRecipeException("Recipe updating error");
        }
    }

    @Transactional
    public Recipe create(RecipeDTO recipeDTO) {
        Recipe recipe = Recipe.builder()
                .title(recipeDTO.getTitle())
                .description(recipeDTO.getDescription())
                .ingredientCapacities(getIngredientCapacities(recipeDTO))
                .parent(getParent(recipeDTO))
                .updateDate(LocalDateTime.now())
                .build();

        saveInstruction(recipe, recipeDTO);

        try {
            return recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new CreationRecipeException("Recipe creating error");
        }
    }

    @Transactional
    public boolean delete(Recipe recipe) {
        try {
            Recipe recipeToDelete = recipeRepository.findById(recipe.getId())
                    .orElseThrow(()-> new RecipeNotFountException(
                            "Recipe with id " + recipe.getId() + " not found"));

            // Delete all ingredient capacities
            recipeToDelete.setIngredientCapacities(null);

            // Delete parent for all children
            recipeToDelete.getChildren().forEach(
                    childRecipe -> childRecipe.setParent(null));

            recipeRepository.delete(recipeToDelete);
        } catch (RuntimeException e) {
            throw new RecipeDeletingException(
                    "Recipe with id " + recipe.getId() + " error");
        }

        return true;
    }
    /**
     * Get parent
     * or return cur element if it has not parent
     */
    @Transactional
    public Recipe getParent(RecipeDTO recipeDTO) {
        return recipeDTO.getParentId() == null ?
            null :
            recipeRepository.findById(recipeDTO.getParentId())
                .orElseThrow(() -> new RecipeNotFountException(
                    "Recipe with id " + recipeDTO.getParentId() + " not found"));
}

    @Transactional
    public List<IngredientCapacity> getIngredientCapacities(RecipeDTO recipeDTO) {
        return recipeDTO.getIngredients().stream()
                .map(this::getIngredientCapacity)
                .collect(Collectors.toList());
    }

    /**
     * Get or create if it does not exist
     */
    @Transactional
    public IngredientCapacity getIngredientCapacity(IngredientDTO ingredientDTO) {
        IngredientCapacity ingredientCapacity = IngredientCapacity.builder()
                .ingredient(getIngredientByName(ingredientDTO.getName()))
                .capacity(ingredientDTO.getCapacity())
                .measure(ingredientDTO.getMeasure())
                .build();
        try {
            return ingredientCapacityRepository.findByIngredientAndMeasureAndCapacity(
                    ingredientCapacity.getIngredient(),
                    ingredientCapacity.getMeasure(),
                    ingredientCapacity.getCapacity())
                    //Ingredient capacity already exists exception
                    //But now this is easier.
                .orElseThrow(RuntimeException::new);
        } catch (RuntimeException e) {
            return ingredientCapacityRepository.save(ingredientCapacity);
        }
    }

    /**
     * Get or create if it does not exist
     */
    @Transactional
    public Ingredient getIngredientByName(String name) {
        try {
            return ingredientRepository.findByName(name)
                    //Ingredient capacity already exists exception
                    //But now this is easier.
                    .orElseThrow(RuntimeException::new);
        } catch (RuntimeException e) {
            return ingredientRepository.save(Ingredient.builder()
                    .name(name)
                    .build());
        }
    }

    public RecipeDTO getDtoById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFountException(
                        "Recipe with id " + recipeId + " not found"));

        List<IngredientDTO> ingredientDTOs = recipe.getIngredientCapacities().stream()
            .map(ingredientCapacity ->
                IngredientDTO.builder()
                    .name(ingredientCapacity.getIngredient().getName())
                    .capacity(ingredientCapacity.getCapacity())
                    .measure(ingredientCapacity.getMeasure())
                    .build())
            .collect(Collectors.toList());

        RecipeDTO recipeDTO = RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .instruction(recipe.getInstruction().getText())
                .ingredients(ingredientDTOs)
                .build();

        if(recipe.getParent() != null)
            recipeDTO.setParentId(recipe.getParent().getId());

        return recipeDTO;
    }


    private void saveInstruction(Recipe recipe, RecipeDTO recipeDTO) {
        instructionRepository.save(Instruction.builder()
                .text(recipeDTO.getInstruction())
                .recipe(recipe)
                .build());
    }

    private void updateInstruction(RecipeDTO recipeDTO) {
        Instruction instruction = instructionRepository.getById(recipeDTO.getId())
                .orElseThrow(() -> new RecipeNotFountException(
                        "Recipe with id " + recipeDTO.getId() + " not found"));

        instruction.setText(recipeDTO.getInstruction());
    }
}
