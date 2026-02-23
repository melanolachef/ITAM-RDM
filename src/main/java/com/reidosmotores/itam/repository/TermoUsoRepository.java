package com.reidosmotores.itam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.TermoUso;

public interface TermoUsoRepository extends JpaRepository<TermoUso, Long> {
    List<TermoUso> findByAssetIdOrderByDataUploadDesc(Long assetId);

    List<TermoUso> findByFuncionarioIdOrderByDataUploadDesc(Long funcionarioId);

    List<TermoUso> findAllByOrderByDataUploadDesc();
}
