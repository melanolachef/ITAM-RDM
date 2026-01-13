package com.reidosmotores.itam.service;

import com.reidosmotores.itam.model.Usuario;
import com.reidosmotores.itam.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Busca no banco
        Usuario usuario = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 2. Retorna um objeto User do Spring Security com os dados do banco
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // Já vem criptografada do banco
                .roles(usuario.getRole())
                .build();
    }
}