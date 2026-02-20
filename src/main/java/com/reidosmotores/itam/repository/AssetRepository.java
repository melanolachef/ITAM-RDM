package com.reidosmotores.itam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reidosmotores.itam.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    long countByStatus(String status);

    long countByTipo(String tipo);

    // Busca ativos vinculados a um funcionário específico
    List<Asset> findByResponsavelId(Long employeeId);

    // Busca ativos sem responsável (em estoque)
    List<Asset> findByResponsavelIsNull();
}