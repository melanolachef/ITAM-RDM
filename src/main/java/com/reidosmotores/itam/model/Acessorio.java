package com.reidosmotores.itam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_acessorios")
public class Acessorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;        // Ex: Mouse Logitech, Teclado Dell
    private String categoria;   // Periférico, Cabos, Áudio
    
    // NOVO: Onde isso está guardado fisicamente?
    private String localizacao; // Ex: Armário TI - Prateleira 2, Gaveta B
    
    private Integer quantidade;     // Saldo atual
    private Integer estoqueMinimo;  // Para gerar alerta de compra

    // Método para saber se precisa comprar
    public boolean isEstoqueBaixo() {
        return quantidade != null && estoqueMinimo != null && quantidade <= estoqueMinimo;
    }
}