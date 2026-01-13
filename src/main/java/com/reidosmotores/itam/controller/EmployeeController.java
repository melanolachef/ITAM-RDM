package com.reidosmotores.itam.controller;

import com.reidosmotores.itam.model.Employee;
import com.reidosmotores.itam.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}