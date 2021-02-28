package com.may.soulrecipes.repository;

import com.may.soulrecipes.entity.Ingredient;
import com.may.soulrecipes.entity.IngredientCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientCapacityRepository extends JpaRepository<IngredientCapacity, Long> {

    Optional<IngredientCapacity> findByIngredientAndMeasureAndCapacity(Ingredient ingredient,
                                                                       String measure,
                                                                       Integer capacity);
}
