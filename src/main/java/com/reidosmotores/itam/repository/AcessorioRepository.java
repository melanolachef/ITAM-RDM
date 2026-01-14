package com.reidosmotores.itam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.Acessorio;

public interface AcessorioRepository extends JpaRepository<Acessorio, Long> {
}