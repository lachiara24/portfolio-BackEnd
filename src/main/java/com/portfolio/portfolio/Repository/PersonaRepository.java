package com.portfolio.portfolio.Repository;

import com.portfolio.portfolio.Model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
