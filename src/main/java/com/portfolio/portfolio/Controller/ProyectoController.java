package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Proyecto;
import com.portfolio.portfolio.Model.Persona;
import com.portfolio.portfolio.Repository.ProyectoRepository;
import com.portfolio.portfolio.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/persona")
public class ProyectoController {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Listado de proyectos por id de persona
    @GetMapping("/{personaId}/proyecto")
    public ResponseEntity<List<Proyecto>> listAllProyectosByPersona(@PathVariable("personaId") final Long id) {
        Optional<Persona> user = personaRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<List<Proyecto>>(HttpStatus.NOT_FOUND);
        }
        List<Proyecto> proyectos = proyectoRepository.findByPersonaId(id);
        if (proyectos.isEmpty()) {
            return new ResponseEntity<List<Proyecto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Proyecto>>(proyectos, HttpStatus.OK);
    }

    // Obtener proyecto por id
    @GetMapping("/{personaId}/proyecto/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable("id") final Long id,
                                                          @PathVariable("personaId") final Long personaId) {
        Optional<Proyecto> proyecto = proyectoRepository.findByIdAndPersonaId(id, personaId);
        if (!proyecto.isPresent()) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Proyecto>(proyecto.get(), HttpStatus.OK);
    }


    // Guardar proyecto de un persona
    @PostMapping(value = "/{personaId}/proyecto", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> createProyecto(@PathVariable("personaId") final Long id, @RequestBody final Proyecto proyecto) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        Persona persona = personaRepository.findById(id).get();
        proyecto.setPersona(persona);
        proyectoRepository.save(proyecto);
        return new ResponseEntity<Proyecto>(proyecto, HttpStatus.CREATED);
    }

    // editar proyecto
    @PutMapping(value = "/{personaId}/proyecto/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> updateUser(@PathVariable("personaId") final Long personaId,
                                                  @PathVariable("id") final Long id,
                                                  @RequestBody Proyecto proyecto) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(personaId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del proyecto
        Optional<Proyecto> currentProyecto = proyectoRepository.findById(id);
        if (!currentProyecto.isPresent()) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        // actualizo proyecto
        currentProyecto.get().setNombre(proyecto.getNombre());
        currentProyecto.get().setDescripcion(proyecto.getDescripcion());
        currentProyecto.get().setLink(proyecto.getLink());
        // guardo proyecto actualizado
        proyectoRepository.saveAndFlush(currentProyecto.get());
        //return ResponseEntity object
        return new ResponseEntity<Proyecto>(currentProyecto.get(), HttpStatus.OK);
    }

    // borrar proyecto
    @DeleteMapping("/{personaId}/proyecto/{id}")
    public ResponseEntity<Proyecto> deleteUser(@PathVariable("id") final Long id,
                                                  @PathVariable("personaId") final Long personaId) {
        // busco si existe el id del persona
        Optional<Persona> currentUser = personaRepository.findById(personaId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del proyecto
        Optional<Proyecto> currentProyecto = proyectoRepository.findById(id);
        if (!currentProyecto.isPresent()) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        proyectoRepository.deleteById(id);
        return new ResponseEntity<Proyecto>(HttpStatus.NO_CONTENT);
    }

}
