package com.portfolio.portfolio.Repository;


import com.portfolio.portfolio.Model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByPersonaId(Long clienteId);
    Optional<Proyecto> findByIdAndPersonaId(Long id, Long ClienteId);
}
