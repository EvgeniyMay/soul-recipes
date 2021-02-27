package com.may.soulrecipes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "recipe_ingredient_capacity",
            joinColumns = { @JoinColumn(name = "recipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_capacity_id")})
    private List<IngredientCapacity> ingredientCapacities;


    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Instruction instruction;

    private String description;

}
