package com.portfolio.portfolio.Repository;

import com.portfolio.portfolio.Model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByUserId(int userId);
}
