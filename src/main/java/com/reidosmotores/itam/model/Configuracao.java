package com.reidosmotores.itam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_configuracao")
public class Configuracao {

    @Id
    private Long id = 1L; 

    private String nomeEmpresa; 
    private String corPrimaria; 

    public Configuracao() {
        this.nomeEmpresa = "ITAM SaaS";
        this.corPrimaria = "#d90429";
    }

    // --- BLINDAGEM: Getters Personalizados ---
    // Se o banco retornar nulo (vazio), usamos o padrão para não quebrar a tela

    public String getNomeEmpresa() {
        if (nomeEmpresa == null || nomeEmpresa.trim().isEmpty()) {
            return "ITAM SaaS";
        }
        return nomeEmpresa;
    }

    public String getCorPrimaria() {
        if (corPrimaria == null || corPrimaria.trim().isEmpty()) {
            return "#d90429"; // Vermelho Rei (Padrão de segurança)
        }
        return corPrimaria;
    }
}