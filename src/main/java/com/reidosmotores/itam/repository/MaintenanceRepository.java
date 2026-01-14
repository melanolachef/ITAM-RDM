package com.reidosmotores.itam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    // Busca manutenções de um ativo específico para a tela de Detalhes
    List<Maintenance> findByAssetIdOrderByDataManutencaoDesc(Long assetId);
}