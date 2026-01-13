package com.reidosmotores.itam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "tb_historico_ativos")
public class AssetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private LocalDateTime dataEvento;
    
    private String descricao; // Ex: "Mudou de João para Maria"

    // Método auxiliar para formatar a data bonitinha na tela
    public String getDataFormatada() {
        return dataEvento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}