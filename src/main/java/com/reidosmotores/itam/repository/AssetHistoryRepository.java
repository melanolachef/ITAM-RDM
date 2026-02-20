package com.reidosmotores.itam.repository;

import com.reidosmotores.itam.model.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
    // Busca o histórico de um ativo específico, ordenando do mais novo para o mais
    // antigo
    List<AssetHistory> findByAssetIdOrderByDataEventoDesc(Long assetId);

    // Busca as últimas transferências (para a página de transferências)
    List<AssetHistory> findTop20ByDescricaoStartingWithOrderByDataEventoDesc(String prefix);
}