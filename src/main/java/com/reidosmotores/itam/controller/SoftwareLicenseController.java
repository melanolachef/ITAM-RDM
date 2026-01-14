package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.SoftwareLicense;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.repository.SoftwareLicenseRepository;

@Controller
@RequestMapping("/licencas")
public class SoftwareLicenseController {

    @Autowired private SoftwareLicenseRepository repository;
    @Autowired private EmployeeRepository employeeRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaLicencas", repository.findAll());
        model.addAttribute("novaLicenca", new SoftwareLicense());
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        return "licencas";
    }

    @PostMapping("/salvar")
    public String salvar(SoftwareLicense license) {
        repository.save(license);
        return "redirect:/licencas";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/licencas";
    }
}