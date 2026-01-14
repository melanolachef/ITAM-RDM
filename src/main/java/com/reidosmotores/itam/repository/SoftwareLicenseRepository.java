package com.reidosmotores.itam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.SoftwareLicense;

public interface SoftwareLicenseRepository extends JpaRepository<SoftwareLicense, Long> {
}