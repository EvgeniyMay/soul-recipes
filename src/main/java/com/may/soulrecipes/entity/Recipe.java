package com.may.soulrecipes.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "recipe_ingredient_capacity",
            joinColumns = { @JoinColumn(name = "recipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_capacity_id")})
    private List<IngredientCapacity> ingredientCapacities;

    @OneToOne
    @MapsId
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Recipe parent;

    @OneToMany(mappedBy = "parent")
    private List<Recipe> children;

    @Column(nullable = false)
    private LocalDateTime updateDate;

}
