package com.reidosmotores.itam.model;

import java.time.LocalDateTime;

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
@Table(name = "tb_asset_fotos")
public class AssetPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    private String nomeArquivo; // Nome original do arquivo

    private String contentType; // Ex: image/jpeg, image/png

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] dados; // Conteúdo binário da imagem

    private LocalDateTime dataUpload;
}
