package com.may.soulrecipes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(columnDefinition = "TEXT")
    private String text;
}
