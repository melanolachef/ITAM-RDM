package com.reidosmotores.itam.repository;

import com.reidosmotores.itam.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    // Aqui você ganha de graça: findAll(), save(), deleteById(), findById()...
    // O Spring cria o SQL sozinho: "SELECT COUNT(*) FROM tb_ativos WHERE status = ?"
    long countByStatus(String status);
    // Se precisar buscar por patrimônio no futuro, é só declarar:
    // Optional<Asset> findByPatrimonio(String patrimonio);
}