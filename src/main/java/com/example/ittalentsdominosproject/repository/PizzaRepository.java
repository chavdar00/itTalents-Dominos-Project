package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.Ingredient;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
