package com.may.soulrecipes.repository;

import com.may.soulrecipes.entity.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {

    Optional<Instruction> getById(Long id);
}
