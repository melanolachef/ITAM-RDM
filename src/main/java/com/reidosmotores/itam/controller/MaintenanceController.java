package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.Maintenance;
import com.reidosmotores.itam.repository.AssetRepository;
import com.reidosmotores.itam.repository.MaintenanceRepository;

@Controller
@RequestMapping("/manutencoes")
public class MaintenanceController {

    @Autowired private MaintenanceRepository repository;
    @Autowired private AssetRepository assetRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaManutencoes", repository.findAll());
        model.addAttribute("novaManutencao", new Maintenance());
        model.addAttribute("listaAtivos", assetRepository.findAll());
        return "manutencoes";
    }

    @PostMapping("/salvar")
    public String salvar(Maintenance maintenance) {
        repository.save(maintenance);
        return "redirect:/manutencoes";
    }
    
    // Atalho para voltar aos detalhes do ativo após salvar (Opcional)
    @PostMapping("/salvarDoDetalhe")
    public String salvarDoDetalhe(Maintenance maintenance) {
        repository.save(maintenance);
        return "redirect:/ativos/detalhes/" + maintenance.getAsset().getId();
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/manutencoes";
    }
}