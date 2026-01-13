package com.reidosmotores.itam.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // O login (ex: admin)

    @Column(nullable = false)
    private String password; // A senha criptografada

    private String role; // O perfil (ex: ADMIN, USER)
}