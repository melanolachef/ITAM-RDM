package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.Usuario;
import com.reidosmotores.itam.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injetamos o criptografador

    // 1. Listar Usuários
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("listaUsuarios", repository.findAll());
        model.addAttribute("novoUsuario", new Usuario());
        return "usuarios";
    }

    // 2. Salvar Novo Usuário (Criptografando a senha)
    @PostMapping("/salvar")
    public String salvarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar no banco
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        
        // Define papel padrão se não vier preenchido
        if (usuario.getRole() == null || usuario.getRole().isEmpty()) {
            usuario.setRole("USER"); 
        }

        repository.save(usuario);
        return "redirect:/usuarios";
    }

    // 3. Excluir Usuário
    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/usuarios";
    }
}