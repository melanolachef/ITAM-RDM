package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.Acessorio;
import com.reidosmotores.itam.repository.AcessorioRepository;

@Controller
@RequestMapping("/acessorios")
public class AcessorioController {

    @Autowired private AcessorioRepository repository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", repository.findAll());
        model.addAttribute("item", new Acessorio());
        return "acessorios";
    }

    @PostMapping("/salvar")
    public String salvar(Acessorio acessorio) {
        repository.save(acessorio);
        return "redirect:/acessorios";
    }
    
    // Adicionar ao estoque (Botão +)
    @GetMapping("/add/{id}")
    public String adicionarEstoque(@PathVariable Long id) {
        Acessorio a = repository.findById(id).orElse(null);
        if (a != null) {
            a.setQuantidade(a.getQuantidade() + 1);
            repository.save(a);
        }
        return "redirect:/acessorios";
    }

    // Remover do estoque (Botão -)
    @GetMapping("/remove/{id}")
    public String removerEstoque(@PathVariable Long id) {
        Acessorio a = repository.findById(id).orElse(null);
        if (a != null && a.getQuantidade() > 0) {
            a.setQuantidade(a.getQuantidade() - 1);
            repository.save(a);
        }
        return "redirect:/acessorios";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/acessorios";
    }
}