package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Persona;
import com.portfolio.portfolio.Model.UserEntity;
import com.portfolio.portfolio.Repository.PersonaRepository;
import com.portfolio.portfolio.Repository.UserRepository;
import com.portfolio.portfolio.dto.PhotosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:5000", "http://localhost:4200"}, allowedHeaders = {"Authorization", "Content-Type"})
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UserRepository userRepository;

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

    //obtener persona por nombre de usuario
    @GetMapping("")
    public ResponseEntity<Persona> getPersonaByUsername (@RequestParam(name = "username") String username) {
        // busco el usuario si existe
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }
        // si existe obtengo la persona por el id del user
        Optional<Persona> persona = personaRepository.findByUserId(user.get().getId());
        if (!persona.isPresent()) {
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Persona>(persona.get(), HttpStatus.OK);
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
        currentUser.get().setApellido(user.getApellido());
        currentUser.get().setProfesion(user.getProfesion());
        currentUser.get().setInfo(user.getInfo());
        currentUser.get().setGithub(user.getGithub());
        currentUser.get().setLinkedin(user.getLinkedin());
        // save currentUser object
        personaRepository.saveAndFlush(currentUser.get());
        //return ResponseEntity object
        return new ResponseEntity<Persona>(currentUser.get(), HttpStatus.OK);
    }

    /// actualizar fotos
    @PutMapping(value = "/{id}/photos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> updatePhotos(@PathVariable("id") final Long id, @RequestBody PhotosDto photos) {
        // fetch user based on id and set it to currentUser object of type Persona
        Optional<Persona> currentUser = personaRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Persona>(HttpStatus.NOT_FOUND);
        }
        // update currentUser object data with user object data
        currentUser.get().setImgPerfil(photos.getImgPerfil());
        currentUser.get().setImgPortada(photos.getImgPortada());
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
