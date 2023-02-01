package com.portfolio.portfolio.Repository;

import com.portfolio.portfolio.Model.Educacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducacionRepository extends JpaRepository<Educacion, Long> {
    List<Educacion> findByPersonaId(Long personaId);
}
