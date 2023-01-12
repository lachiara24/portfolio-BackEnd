package com.portfolio.portfolio.Repository;

import com.portfolio.portfolio.Model.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {
    List<Experiencia> findByPersonaId(Long clienteId);
    Optional<Experiencia> findByIdAndPersonaId(Long id, Long ClienteId);
}
