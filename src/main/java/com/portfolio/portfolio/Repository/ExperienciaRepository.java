package com.portfolio.portfolio.Repository;

import com.portfolio.portfolio.Model.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {
    List<Experiencia> findByPersonaId(Long clienteId);
}
