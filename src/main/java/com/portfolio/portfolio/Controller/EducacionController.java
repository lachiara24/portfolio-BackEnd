package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Educacion;
import com.portfolio.portfolio.Model.Persona;
import com.portfolio.portfolio.Repository.EducacionRepository;
import com.portfolio.portfolio.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = Enviroment.URL, allowedHeaders = {"Authorization", "Content-Type"})
@RequestMapping("/api/persona")
public class EducacionController {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EducacionRepository educacionRepository;

    // Listado de educacions por id de persona
    @GetMapping("/{personaId}/educacion")
    public ResponseEntity<List<Educacion>> listAllEducacionsByPersona(@PathVariable("personaId") final Long id) {
        Optional<Persona> user = personaRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<List<Educacion>>(HttpStatus.NOT_FOUND);
        }
        List<Educacion> educacions = educacionRepository.findByPersonaId(id);
        if (educacions.isEmpty()) {
            return new ResponseEntity<List<Educacion>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Educacion>>(educacions, HttpStatus.OK);
    }

    // Obtener educacion por id
    @GetMapping("/educacion/{id}")
    public ResponseEntity<Educacion> getEducacionById(@PathVariable("id") final Long id) {
        Optional<Educacion> educacion = educacionRepository.findById(id);
        if (!educacion.isPresent()) {
            return new ResponseEntity<Educacion>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Educacion>(educacion.get(), HttpStatus.OK);
    }


    // Guardar educacion de un persona
    @PostMapping(value = "/{personaId}/educacion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Educacion> createEducacion(@PathVariable("personaId") final Long id, @RequestBody final Educacion educacion) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Educacion>(HttpStatus.NOT_FOUND);
        }
        Persona persona = personaRepository.findById(id).get();
        educacion.setPersona(persona);
        educacionRepository.save(educacion);
        return new ResponseEntity<Educacion>(educacion, HttpStatus.CREATED);
    }

    // editar educacion
    @PutMapping(value = "/educacion/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Educacion> updateUser(@PathVariable("id") final Long id,
                                                  @RequestBody Educacion educacion) {
        // busco si existe el id del educacion
        Optional<Educacion> currentEducacion = educacionRepository.findById(id);
        if (!currentEducacion.isPresent()) {
            return new ResponseEntity<Educacion>(HttpStatus.NOT_FOUND);
        }
        // actualizo educacion
        currentEducacion.get().setInstitucion(educacion.getInstitucion());
        currentEducacion.get().setTitulo(educacion.getTitulo());
        currentEducacion.get().setFechaInicio(educacion.getFechaInicio());
        currentEducacion.get().setFechaFin(educacion.getFechaFin());
        // guardo educacion actualizado
        educacionRepository.saveAndFlush(currentEducacion.get());
        //return ResponseEntity object
        return new ResponseEntity<Educacion>(currentEducacion.get(), HttpStatus.OK);
    }

    // borrar educacion
    @DeleteMapping("/{personaId}/educacion/{id}")
    public ResponseEntity<Educacion> deleteUser(@PathVariable("id") final Long id,
                                                  @PathVariable("personaId") final Long personaId) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(personaId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Educacion>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del educacion
        Optional<Educacion> currentEducacion = educacionRepository.findById(id);
        if (!currentEducacion.isPresent()) {
            return new ResponseEntity<Educacion>(HttpStatus.NOT_FOUND);
        }
        educacionRepository.deleteById(id);
        return new ResponseEntity<Educacion>(HttpStatus.NO_CONTENT);
    }
}
