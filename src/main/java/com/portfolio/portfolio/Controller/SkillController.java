package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Skill;
import com.portfolio.portfolio.Model.Persona;
import com.portfolio.portfolio.Repository.SkillRepository;
import com.portfolio.portfolio.Repository.PersonaRepository;
import com.portfolio.portfolio.dto.PhotosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = Enviroment.URL)
@RequestMapping("/api/persona")
public class SkillController {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private SkillRepository skillRepository;

    // Listado de skills por id de persona
    @GetMapping("/{personaId}/skill")
    public ResponseEntity<List<Skill>> listAllSkillsByPersona(@PathVariable("personaId") final Long id) {
        Optional<Persona> user = personaRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<List<Skill>>(HttpStatus.NOT_FOUND);
        }
        List<Skill> skills = skillRepository.findByPersonaId(id);
        if (skills.isEmpty()) {
            return new ResponseEntity<List<Skill>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Skill>>(skills, HttpStatus.OK);
    }

    // Obtener skill por id
    @GetMapping("/skill/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") final Integer id) {
        Optional<Skill> skill = skillRepository.findById(id);
        if (!skill.isPresent()) {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Skill>(skill.get(), HttpStatus.OK);
    }


    // Guardar skill de un persona
    @PostMapping(value = "/{personaId}/skill", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Skill> createSkill(@PathVariable("personaId") final Long id, @RequestBody final Skill skill) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
        Persona persona = personaRepository.findById(id).get();
        skill.setPersona(persona);
        skillRepository.save(skill);
        return new ResponseEntity<Skill>(skill, HttpStatus.CREATED);
    }

    // editar skill
    @PutMapping(value = "/skill/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Skill> updateUser(@PathVariable("id") final Integer id,
                                                  @RequestBody Skill skill) {

        // busco si existe el id del skill
        Optional<Skill> currentSkill = skillRepository.findById(id);
        if (!currentSkill.isPresent()) {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
        // actualizo skill
        currentSkill.get().setNombre(skill.getNombre());
        currentSkill.get().setPorcentaje(skill.getPorcentaje());
        currentSkill.get().setImg(skill.getImg());
        // guardo skill actualizado
        skillRepository.saveAndFlush(currentSkill.get());
        //return ResponseEntity object
        return new ResponseEntity<Skill>(currentSkill.get(), HttpStatus.OK);
    }

    /// editar foto de skill

    @PutMapping(value = "/skill/{id}/photo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Skill> updatePhoto(@PathVariable("id") final Integer id,
                                             @RequestBody PhotosDto img) {

        // busco si existe el id del skill
        Optional<Skill> currentSkill = skillRepository.findById(id);
        if (!currentSkill.isPresent()) {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
        // actualizo skill
        currentSkill.get().setImg(img.getImg());
        // guardo skill actualizado
        skillRepository.saveAndFlush(currentSkill.get());
        //return ResponseEntity object
        return new ResponseEntity<Skill>(currentSkill.get(), HttpStatus.OK);
    }

    // borrar skill
    @DeleteMapping("/{personaId}/skill/{id}")
    public ResponseEntity<Skill> deleteUser(@PathVariable("id") final Integer id,
                                                  @PathVariable("personaId") final Long personaId) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(personaId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del skill
        Optional<Skill> currentSkill = skillRepository.findById(id);
        if (!currentSkill.isPresent()) {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
        skillRepository.deleteById(id);
        return new ResponseEntity<Skill>(HttpStatus.NO_CONTENT);
    }
}
