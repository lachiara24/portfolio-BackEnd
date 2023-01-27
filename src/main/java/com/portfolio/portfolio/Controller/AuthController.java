package com.portfolio.portfolio.Controller;

import com.portfolio.portfolio.Model.Persona;
import com.portfolio.portfolio.Model.Role;
import com.portfolio.portfolio.Model.UserEntity;
import com.portfolio.portfolio.Repository.PersonaRepository;
import com.portfolio.portfolio.Repository.RoleRepository;
import com.portfolio.portfolio.Repository.UserRepository;
import com.portfolio.portfolio.dto.AuthResponseDto;
import com.portfolio.portfolio.dto.LoginDto;
import com.portfolio.portfolio.dto.RegisterDto;
import com.portfolio.portfolio.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtGenerator jwtGenerator;

    private PersonaRepository personaRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtGenerator jwtGenerator,
                          PersonaRepository personaRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.personaRepository = personaRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){

            return new ResponseEntity<String>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);
        Persona persona = new Persona(user.getUsername(), user);
        personaRepository.save(persona);

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        // busco el usuario si existe
        Optional<UserEntity> user = userRepository.findByUsername(authentication.getName());
        return new ResponseEntity<>(new AuthResponseDto(token, authentication.getName(), authentication.getAuthorities(), user.get().getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> currentUserName(Authentication authentication) {
        return new ResponseEntity<String>(authentication.getName(), HttpStatus.OK);
    }
}
