package com.may.soulrecipes.repository;

import com.may.soulrecipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
