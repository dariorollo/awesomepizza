package com.example.demo.repositories;

import com.example.demo.entities.AdditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionRepository extends JpaRepository<AdditionEntity, Integer> {
}
