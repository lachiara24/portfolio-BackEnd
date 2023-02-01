package com.portfolio.portfolio.Repository;

import com.portfolio.portfolio.Model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    List<Skill> findByPersonaId(Long personaId);
}
