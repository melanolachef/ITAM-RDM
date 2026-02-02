package com.reidosmotores.itam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.SolicitacaoCompra;

public interface SolicitacaoCompraRepository extends JpaRepository<SolicitacaoCompra, Long> {
}