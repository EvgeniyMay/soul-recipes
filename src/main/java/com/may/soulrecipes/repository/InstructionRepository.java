package com.may.soulrecipes.repository;

import com.may.soulrecipes.entity.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
}
