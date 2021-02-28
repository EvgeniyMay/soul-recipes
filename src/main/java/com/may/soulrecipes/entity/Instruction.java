package com.may.soulrecipes.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "instruction", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Recipe recipe;

    @Column(columnDefinition = "TEXT")
    private String text;
}
