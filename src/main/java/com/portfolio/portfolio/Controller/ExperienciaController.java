package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Experiencia;
import com.portfolio.portfolio.Model.Persona;
import com.portfolio.portfolio.Repository.ExperienciaRepository;
import com.portfolio.portfolio.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/api/persona")
public class ExperienciaController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ExperienciaRepository experienciaRepository;

    // Listado de experiencias por id de persona
    @GetMapping("/{personaId}/experiencia")
    public ResponseEntity<List<Experiencia>> listAllExperienciasByPersona(@PathVariable("personaId") final Long id) {
        Optional<Persona> user = personaRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<List<Experiencia>>(HttpStatus.NOT_FOUND);
        }
        List<Experiencia> experiencias = experienciaRepository.findByPersonaId(id);
        if (experiencias.isEmpty()) {
            return new ResponseEntity<List<Experiencia>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Experiencia>>(experiencias, HttpStatus.OK);
    }

    // Obtener experiencia por id
    @GetMapping("/experiencia/{id}")
    public ResponseEntity<Experiencia> getExperienciaById(@PathVariable("id") final Long id) {
        Optional<Experiencia> experiencia = experienciaRepository.findById(id);
        if (!experiencia.isPresent()) {
            return new ResponseEntity<Experiencia>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Experiencia>(experiencia.get(), HttpStatus.OK);
    }


    // Guardar experiencia de un persona
    @PostMapping(value = "/{personaId}/experiencia", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Experiencia> createExperiencia(@PathVariable("personaId") final Long id, @RequestBody final Experiencia experiencia) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Experiencia>(HttpStatus.NOT_FOUND);
        }
        Persona persona = personaRepository.findById(id).get();
        experiencia.setPersona(persona);
        experienciaRepository.save(experiencia);
        return new ResponseEntity<Experiencia>(experiencia, HttpStatus.CREATED);
    }

    // editar experiencia
    @PutMapping(value = "/experiencia/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Experiencia> updateUser(@PathVariable("id") final Long id,
                                           @RequestBody Experiencia experiencia) {

        // busco si existe el id del experiencia
        Optional<Experiencia> currentExperiencia = experienciaRepository.findById(id);
        if (!currentExperiencia.isPresent()) {
            return new ResponseEntity<Experiencia>(HttpStatus.NOT_FOUND);
        }
        // actualizo experiencia
        currentExperiencia.get().setEmpresa(experiencia.getEmpresa());
        currentExperiencia.get().setPuesto(experiencia.getPuesto());
        currentExperiencia.get().setUbicacion(experiencia.getUbicacion());
        currentExperiencia.get().setFechaInicio(experiencia.getFechaInicio());
        currentExperiencia.get().setFechaFin(experiencia.getFechaFin());
        // guardo experiencia actualizado
        experienciaRepository.saveAndFlush(currentExperiencia.get());
        //return ResponseEntity object
        return new ResponseEntity<Experiencia>(currentExperiencia.get(), HttpStatus.OK);
    }

    // borrar experiencia
    @DeleteMapping("/{personaId}/experiencia/{id}")
    public ResponseEntity<Experiencia> deleteUser(@PathVariable("id") final Long id,
                                           @PathVariable("personaId") final Long personaId) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(personaId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Experiencia>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del experiencia
        Optional<Experiencia> currentExperiencia = experienciaRepository.findById(id);
        if (!currentExperiencia.isPresent()) {
            return new ResponseEntity<Experiencia>(HttpStatus.NOT_FOUND);
        }
        experienciaRepository.deleteById(id);
        return new ResponseEntity<Experiencia>(HttpStatus.NO_CONTENT);
    }

}
