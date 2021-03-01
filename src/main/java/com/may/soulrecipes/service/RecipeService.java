package com.may.soulrecipes.service;

import com.may.soulrecipes.dto.IngredientDTO;
import com.may.soulrecipes.dto.RecipeDTO;
import com.may.soulrecipes.entity.Ingredient;
import com.may.soulrecipes.entity.IngredientCapacity;
import com.may.soulrecipes.entity.Instruction;
import com.may.soulrecipes.entity.Recipe;
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
                //ToDo | Create special exception
                .orElseThrow(RuntimeException::new);
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

        return recipeRepository.save(recipe);
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

        return recipeRepository.save(recipe);
    }

    private void saveInstruction(Recipe recipe, RecipeDTO recipeDTO) {
        instructionRepository.save(Instruction.builder()
            .text(recipeDTO.getInstruction())
            .recipe(recipe)
            .build());
    }

    private void updateInstruction(RecipeDTO recipeDTO) {
        try {
            instructionRepository.getById(recipeDTO.getId())
                    //ToDo | Create special exception
                    .orElseThrow(RuntimeException::new)
                    .setText(recipeDTO.getInstruction());
        } catch (RuntimeException e) {
            //ToDo
            e.printStackTrace();
        }
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
                        .orElseThrow(RuntimeException::new);
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
                    //ToDo | Create special exception
                    .orElseThrow(RuntimeException::new);
        } catch (RuntimeException e) {
            return ingredientRepository.save(Ingredient.builder()
                    .name(name)
                    .build());
        }
    }

    public RecipeDTO getDtoById(Long recipeId) {
        //ToDo | Exception
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RuntimeException::new);

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

}
