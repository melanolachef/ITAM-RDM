package com.reidosmotores.itam.repository;

import com.reidosmotores.itam.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca o usuário pelo login
    Optional<Usuario> findByUsername(String username);
}