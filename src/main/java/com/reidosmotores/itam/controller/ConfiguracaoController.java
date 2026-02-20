package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reidosmotores.itam.model.Configuracao;
import com.reidosmotores.itam.repository.ConfiguracaoRepository;

@Controller
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository repository;

    @GetMapping("/configuracoes")
    public String abrirConfiguracoes(Model model) {
        // Busca a configuração ID 1. Se não existir, cria uma nova em memória para não
        // dar erro.
        Configuracao config = repository.findById(1L).orElse(new Configuracao());
        model.addAttribute("config", config);
        return "configuracoes";
    }

    @PostMapping("/configuracoes/salvar")
    public String salvar(Configuracao config) {
        config.setId(1L); // Garante que sempre estamos editando a mesma linha (ID 1)
        repository.save(config);
        return "redirect:/configuracoes"; // Recarrega a página para aplicar a nova cor
    }
}