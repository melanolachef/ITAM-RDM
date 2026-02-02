package com.reidosmotores.itam.repository;

import com.reidosmotores.itam.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AtivoRepository extends JpaRepository<Asset, Long> {
    
    // NOVO: Busca exata pelo nome do responsável
    List<Asset> findByResponsavel(String responsavel);
    
}