package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.Linha;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.repository.LinhaRepository;

@Controller
@RequestMapping("/linhas")
public class LinhaController {

    @Autowired private LinhaRepository repository;
    @Autowired private EmployeeRepository employeeRepository;

    @GetMapping
    public String listarLinhas(Model model) {
        model.addAttribute("listaLinhas", repository.findAll());
        model.addAttribute("novaLinha", new Linha());
        // Precisamos da lista de funcionários para o "select" de responsável
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        return "linhas";
    }

    @PostMapping("/salvar")
    public String salvarLinha(Linha linha) {
        repository.save(linha);
        return "redirect:/linhas";
    }

    @GetMapping("/deletar/{id}")
    public String deletarLinha(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/linhas";
    }
}