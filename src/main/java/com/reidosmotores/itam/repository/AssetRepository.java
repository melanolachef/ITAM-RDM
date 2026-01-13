package com.reidosmotores.itam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reidosmotores.itam.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    long countByStatus(String status);

    
    long countByTipo(String tipo);
    
}