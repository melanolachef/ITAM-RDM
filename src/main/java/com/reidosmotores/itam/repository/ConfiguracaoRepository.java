package com.reidosmotores.itam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.Configuracao;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
}