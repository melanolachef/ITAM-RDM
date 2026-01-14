package com.reidosmotores.itam.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reidosmotores.itam.model.Asset;
import com.reidosmotores.itam.model.AssetHistory;
import com.reidosmotores.itam.model.Maintenance;
import com.reidosmotores.itam.repository.AssetHistoryRepository;
import com.reidosmotores.itam.repository.AssetRepository;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.repository.MaintenanceRepository;
import com.reidosmotores.itam.service.QRCodeService;

@Controller
@RequestMapping("/ativos")
public class AssetController {

    @Autowired private AssetRepository repository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private AssetHistoryRepository historyRepository;
    @Autowired private MaintenanceRepository maintenanceRepository; // NOVO
    @Autowired private QRCodeService qrCodeService;

    // --- MÉTODOS AUXILIARES ---
    private void carregarDashboard(Model model) {
        model.addAttribute("qtdTotal", repository.count());
        model.addAttribute("qtdDisponivel", repository.countByStatus("DISPONIVEL"));
        model.addAttribute("qtdManutencao", repository.countByStatus("MANUTENCAO"));
        model.addAttribute("qtdEmUso", repository.countByStatus("EM_USO"));
        model.addAttribute("qtdDescarte", repository.countByStatus("DESCARTE"));

        model.addAttribute("qtdNotebook", repository.countByTipo("Notebook"));
        model.addAttribute("qtdDesktop", repository.countByTipo("Desktop"));
        model.addAttribute("qtdMonitor", repository.countByTipo("Monitor"));
        model.addAttribute("qtdCelular", repository.countByTipo("Celular"));
        model.addAttribute("qtdImpressora", repository.countByTipo("Impressora"));
    }

    // --- ROTAS PRINCIPAIS ---
    @GetMapping
    public String listarAtivos(Model model) {
        model.addAttribute("listaAtivos", repository.findAll());
        model.addAttribute("novoAtivo", new Asset());
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        carregarDashboard(model);
        return "ativos";
    }

    @PostMapping("/salvar")
    public String salvarAtivo(Asset asset) {
        if (asset.getId() != null) {
            Asset assetAntigo = repository.findById(asset.getId()).orElse(null);
            if (assetAntigo != null) {
                Long idAntigo = (assetAntigo.getResponsavel() != null) ? assetAntigo.getResponsavel().getId() : null;
                Long idNovo = (asset.getResponsavel() != null) ? asset.getResponsavel().getId() : null;

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

    @GetMapping("/editar/{id}")
    public String editarAtivo(@PathVariable Long id, Model model) {
        Asset ativoExistente = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("listaAtivos", repository.findAll());
        model.addAttribute("novoAtivo", ativoExistente);
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        carregarDashboard(model);
        return "ativos";
    }

    @GetMapping("/deletar/{id}")
    public String deletarAtivo(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/ativos";
    }

    @GetMapping("/qrcode/{id}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable Long id) {
        try {
            Asset asset = repository.findById(id).orElseThrow(() -> new RuntimeException("Ativo não encontrado"));
            String responsavelNome = (asset.getResponsavel() != null) ? asset.getResponsavel().getNome() : "Estoque";
            String textoQRCode = "ID:" + asset.getId() + "\nPat:" + asset.getPatrimonio() + "\nResp:" + responsavelNome;
            byte[] imagem = qrCodeService.gerarQRCode(textoQRCode, 200, 200);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imagem);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // --- ATUALIZADO: Agora carrega o histórico de Manutenção também ---
    @GetMapping("/detalhes/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Asset asset = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        List<AssetHistory> historico = historyRepository.findByAssetIdOrderByDataEventoDesc(id);
        List<Maintenance> manutencoes = maintenanceRepository.findByAssetIdOrderByDataManutencaoDesc(id); // NOVO
        
        model.addAttribute("ativo", asset);
        model.addAttribute("historico", historico);
        model.addAttribute("manutencoes", manutencoes); // NOVO
        
        return "detalhes";
    }
}