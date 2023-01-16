package com.portfolio.portfolio.Model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "password")
    private String password;
}
