package com.reidosmotores.itam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reidosmotores.itam.model.Linha;

@Repository
public interface LinhaRepository extends JpaRepository<Linha, Long> {
}