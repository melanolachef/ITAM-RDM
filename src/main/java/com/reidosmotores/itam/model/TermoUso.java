package com.reidosmotores.itam.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_termos_uso")
public class TermoUso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Employee funcionario;

    private String nomeArquivo; // Nome original do arquivo
    private String contentType; // Ex: application/pdf, image/jpeg

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] dados; // Conteúdo binário do documento

    private LocalDateTime dataUpload;
    private String observacao; // Ex: "Termo de responsabilidade Notebook Dell"

    public String getDataFormatada() {
        if (dataUpload == null)
            return "";
        return dataUpload.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
