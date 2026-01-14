package com.reidosmotores.itam.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_manutencoes")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset; // O equipamento que quebrou

    private String descricao; // Ex: Troca de Tela, Formatação
    private String fornecedor; // Ex: Assistência Técnica XYZ
    
    @Column(precision = 10, scale = 2)
    private BigDecimal custo; // Quanto custou

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataManutencao;
}