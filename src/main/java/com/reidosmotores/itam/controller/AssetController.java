package com.reidosmotores.itam.controller;

import com.reidosmotores.itam.model.Asset;
import com.reidosmotores.itam.model.AssetHistory;
import com.reidosmotores.itam.repository.AssetRepository;
import com.reidosmotores.itam.repository.AssetHistoryRepository;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/ativos")
public class AssetController {

    @Autowired private AssetRepository repository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private AssetHistoryRepository historyRepository;
    @Autowired private QRCodeService qrCodeService;

    // --- MÉTODOS AUXILIARES ---
    
    // Método para carregar os números do Dashboard (usado na listagem e na edição)
    private void carregarDashboard(Model model) {
        model.addAttribute("qtdTotal", repository.count());
        // Lembre-se: Para isso funcionar, o método countByStatus deve existir no Repository
        model.addAttribute("qtdDisponivel", repository.countByStatus("DISPONIVEL"));
        model.addAttribute("qtdManutencao", repository.countByStatus("MANUTENCAO"));
    }

    // --- ROTAS PRINCIPAIS ---

    // 1. Tela Inicial (Lista + Formulário Vazio + Dashboard)
    @GetMapping
    public String listarAtivos(Model model) {
        model.addAttribute("listaAtivos", repository.findAll());
        model.addAttribute("novoAtivo", new Asset()); // Formulário em branco
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        
        carregarDashboard(model); // Carrega os cards coloridos
        
        return "ativos";
    }

    // 2. Salvar (Cria ou Atualiza + Gera Histórico se mudar dono)
    @PostMapping("/salvar")
    public String salvarAtivo(Asset asset) {
        // Se for Edição (tem ID), verifica se trocou de responsável
        if (asset.getId() != null) {
            Asset assetAntigo = repository.findById(asset.getId()).orElse(null);
            
            if (assetAntigo != null) {
                Long idAntigo = (assetAntigo.getResponsavel() != null) ? assetAntigo.getResponsavel().getId() : null;
                Long idNovo = (asset.getResponsavel() != null) ? asset.getResponsavel().getId() : null;

                // Se mudou o dono, salva no histórico
                if (!Objects.equals(idAntigo, idNovo)) {
                    AssetHistory historico = new AssetHistory();
                    historico.setAsset(asset);
                    historico.setDataEvento(LocalDateTime.now());
                    
                    String nomeAntigo = (assetAntigo.getResponsavel() != null) ? assetAntigo.getResponsavel().getNome() : "Estoque";
                    String nomeNovo = (asset.getResponsavel() != null) ? asset.getResponsavel().getNome() : "Estoque";
                    
                    historico.setDescricao("Transferência: Saiu de " + nomeAntigo + " para " + nomeNovo);
                    historyRepository.save(historico);
                }
            }
        } 

        repository.save(asset);
        return "redirect:/ativos";
    }

    // 3. Editar (Carrega dados no formulário)
    @GetMapping("/editar/{id}")
    public String editarAtivo(@PathVariable Long id, Model model) {
        Asset ativoExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        
        model.addAttribute("listaAtivos", repository.findAll());
        model.addAttribute("novoAtivo", ativoExistente); // Preenche o formulário com dados existentes
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        
        carregarDashboard(model); // Mantém os números visíveis
        
        return "ativos";
    }

    // 4. Excluir
    @GetMapping("/deletar/{id}")
    public String deletarAtivo(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/ativos";
    }

    // --- ROTAS DE UTILITÁRIOS (QR Code, Detalhes) ---

    // 5. Gerar QR Code (Imagem PNG)
    @GetMapping("/qrcode/{id}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable Long id) {
        try {
            Asset asset = repository.findById(id).orElseThrow(() -> new RuntimeException("Ativo não encontrado"));
            String responsavelNome = (asset.getResponsavel() != null) ? asset.getResponsavel().getNome() : "Estoque";
            
            // Texto embutido no QR Code
            String textoQRCode = "ID:" + asset.getId() + "\nPat:" + asset.getPatrimonio() + "\nResp:" + responsavelNome;
            
            byte[] imagem = qrCodeService.gerarQRCode(textoQRCode, 200, 200);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imagem);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 6. Ver Detalhes e Histórico (Tela separada)
    @GetMapping("/detalhes/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Asset asset = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        List<AssetHistory> historico = historyRepository.findByAssetIdOrderByDataEventoDesc(id);
        
        model.addAttribute("ativo", asset);
        model.addAttribute("historico", historico);
        
        return "detalhes";
    }
}