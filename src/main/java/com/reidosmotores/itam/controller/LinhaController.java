package com.reidosmotores.itam.controller;

import com.reidosmotores.itam.model.Linha;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/linhas")
public class LinhaController {

    @Autowired
    private LinhaRepository linhaRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public String listarLinhas(Model model) {
        model.addAttribute("linhas", linhaRepository.findAll());
        model.addAttribute("funcionarios", employeeRepository.findAll());
        model.addAttribute("novaLinha", new Linha()); // Formulário vazio
        return "linhas";
    }

    @PostMapping("/salvar")
    public String salvarLinha(Linha linha, RedirectAttributes attributes) {
        try {
            linhaRepository.save(linha);
            attributes.addFlashAttribute("mensagem", "Linha salva com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao salvar linha: " + e.getMessage());
        }
        return "redirect:/linhas";
    }

    // --- NOVO MÉTODO DE EDITAR ---
    @GetMapping("/editar/{id}")
    public String editarLinha(@PathVariable Long id, Model model) {
        // Busca a linha pelo ID
        Linha linha = linhaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        
        // Preenche o objeto "novaLinha" com os dados do banco (assim o formulário aparece preenchido)
        model.addAttribute("novaLinha", linha);
        
        // Carrega as listas novamente para a tabela e o dropdown não sumirem
        model.addAttribute("linhas", linhaRepository.findAll());
        model.addAttribute("funcionarios", employeeRepository.findAll());
        
        return "linhas"; // Retorna para a mesma tela
    }

    @GetMapping("/deletar/{id}")
    public String deletarLinha(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            linhaRepository.deleteById(id);
            attributes.addFlashAttribute("mensagem", "Linha removida!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao excluir.");
        }
        return "redirect:/linhas";
    }
}