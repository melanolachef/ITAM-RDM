package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.reidosmotores.itam.model.SolicitacaoCompra;
import com.reidosmotores.itam.repository.SolicitacaoCompraRepository;

@Controller
public class ComprasController {

    @Autowired
    private SolicitacaoCompraRepository repository;

    @GetMapping("/compras")
    public String listarCompras(Model model) {
        model.addAttribute("lista", repository.findAll());
        model.addAttribute("novaSolicitacao", new SolicitacaoCompra());
        return "compras";
    }

    @PostMapping("/compras/salvar")
    public String salvar(SolicitacaoCompra solicitacao) {
        if (solicitacao.getId() == null) {
            solicitacao.setStatus("PENDENTE"); // Padrão ao criar
        }
        repository.save(solicitacao);
        return "redirect:/compras";
    }

    // Avançar status (Simulação simples de workflow)
    @GetMapping("/compras/avancar/{id}")
    public String avancarStatus(@PathVariable Long id) {
        SolicitacaoCompra s = repository.findById(id).orElse(null);
        if (s != null) {
            if (s.getStatus().equals("PENDENTE")) s.setStatus("EM_COTACAO");
            else if (s.getStatus().equals("EM_COTACAO")) s.setStatus("APROVADO");
            else if (s.getStatus().equals("APROVADO")) s.setStatus("CONCLUIDO");
            repository.save(s);
        }
        return "redirect:/compras";
    }
    
    @GetMapping("/compras/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/compras";
    }
}