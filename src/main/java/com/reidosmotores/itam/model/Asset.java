package com.reidosmotores.itam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_ativos")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String patrimonio; // Ex: RM-001

    private String tipo;       // Ex: Notebook
    private String marca;      // Ex: Dell
    private String modelo;     // Ex: G15
    private String numeroSerie;
    
    private String status;     // Ex: DISPONIVEL, EM_USO

    private LocalDate dataCompra;

    // NOVO: Relacionamento com Funcionário
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee responsavel;
}