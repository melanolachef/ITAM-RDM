package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.Employee;
import com.reidosmotores.itam.repository.EmployeeRepository;

@Controller
@RequestMapping("/funcionarios")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @GetMapping
    public String listarFuncionarios(Model model) {
        model.addAttribute("listaFuncionarios", repository.findAll());
        model.addAttribute("novoFuncionario", new Employee());
        return "funcionarios";
    }

    @PostMapping("/salvar")
    public String salvarFuncionario(Employee employee) {
        repository.save(employee);
        return "redirect:/funcionarios";
    }

    // --- NOVO MÉTODO DE EXCLUSÃO ---
    @GetMapping("/deletar/{id}")
    public String deletarFuncionario(@PathVariable Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            // Se der erro (ex: funcionário tem notebook vinculado), 
            // apenas ignora e volta pra lista por enquanto.
            System.out.println("Erro ao excluir: O funcionário possui vínculos.");
        }
        return "redirect:/funcionarios";
    }
}