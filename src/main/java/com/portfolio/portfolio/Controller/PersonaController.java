package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Persona;
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
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/")
    public ResponseEntity<List<Persona>> listAllUsers() {
        List<Persona> users = personaRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<Persona>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Persona>>(users, HttpStatus.OK);
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> createUser(@RequestBody final Persona user) {
        personaRepository.save(user);
        return new ResponseEntity<Persona>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getUserById(@PathVariable("id") final Long id) {
        Optional<Persona> user = personaRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Persona>(user.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> updateUser(@PathVariable("id") final Long id, @RequestBody Persona user) {
        // fetch user based on id and set it to currentUser object of type Persona
        Optional<Persona> currentUser = personaRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }
        // update currentUser object data with user object data
        currentUser.get().setNombre(user.getNombre());
        currentUser.get().setProfesion(user.getProfesion());
        currentUser.get().setInfo(user.getInfo());
        // save currentUser object
        personaRepository.saveAndFlush(currentUser.get());
        //return ResponseEntity object
        return new ResponseEntity<Persona>(currentUser.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Persona> deleteUser(@PathVariable("id") final Long id) {
        Optional<Persona> user = personaRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }
        personaRepository.deleteById(id);
        return new ResponseEntity<Persona>(HttpStatus.NO_CONTENT);
    }
}
