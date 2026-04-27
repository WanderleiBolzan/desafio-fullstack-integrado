package com.example.ejb.repository;

import com.example.ejb.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {
    // Ao estender JpaRepository, o método findAll() é criado automaticamente!
}