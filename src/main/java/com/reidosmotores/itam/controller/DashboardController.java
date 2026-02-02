package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;        // <--- Importante
import org.springframework.ui.Model;  // <--- Importante
import org.springframework.web.bind.annotation.GetMapping;

import com.reidosmotores.itam.repository.AcessorioRepository;
import com.reidosmotores.itam.repository.AssetRepository;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.repository.SolicitacaoCompraRepository;

@Controller
public class DashboardController {

    @Autowired private AcessorioRepository acessorioRepo;
    @Autowired private SolicitacaoCompraRepository compraRepo;
    
    // Injetando os repositórios que faltavam para contar os dados reais
    @Autowired private AssetRepository ativoRepo;
    @Autowired private EmployeeRepository funcionarioRepo;

    // Aceita tanto a raiz quanto o /dashboard
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        
        // --- DADOS REAIS DO BANCO DE DADOS ---
        
        // Conta quantos registros existem na tabela (Ex: 13)
        model.addAttribute("totalAtivos", ativoRepo.count());
        
        // Conta quantos funcionários
        model.addAttribute("totalFuncionarios", funcionarioRepo.count());
        
        // Conta quantos acessórios
        model.addAttribute("totalAcessorios", acessorioRepo.count());
        
        // Conta processos de compra
        model.addAttribute("totalProcessos", compraRepo.count());

        // Lista de compras recentes para a tabela
        model.addAttribute("comprasRecentes", compraRepo.findAll());
        
        return "dashboard";
    }



}

