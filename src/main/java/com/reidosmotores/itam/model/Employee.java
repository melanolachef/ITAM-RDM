package com.reidosmotores.itam.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_funcionarios")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    private String departamento; // Ex: TI, Financeiro, Oficina
    private String email;
}