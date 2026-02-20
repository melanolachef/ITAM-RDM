package com.reidosmotores.itam.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reidosmotores.itam.model.Asset;
import com.reidosmotores.itam.model.AssetHistory;
import com.reidosmotores.itam.model.Employee;
import com.reidosmotores.itam.repository.AssetHistoryRepository;
import com.reidosmotores.itam.repository.AssetRepository;
import com.reidosmotores.itam.repository.EmployeeRepository;

@Controller
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AssetHistoryRepository historyRepository;

    @GetMapping
    public String paginaTransferencias(Model model) {
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        model.addAttribute("ativosEstoque", assetRepository.findByResponsavelIsNull());

        // Últimas 20 transferências
        List<AssetHistory> ultimasTransferencias = historyRepository
                .findTop20ByDescricaoStartingWithOrderByDataEventoDesc("Transferência:");
        model.addAttribute("ultimasTransferencias", ultimasTransferencias);

        return "transferencias";
    }

    // Endpoint AJAX: retorna ativos de um funcionário específico
    @GetMapping("/ativos-por-funcionario/{id}")
    @ResponseBody
    public List<Map<String, Object>> ativosPorFuncionario(@PathVariable Long id) {
        List<Asset> ativos = assetRepository.findByResponsavelId(id);
        return ativos.stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("patrimonio", a.getPatrimonio());
            map.put("tipo", a.getTipo());
            map.put("modelo", a.getModelo());
            return map;
        }).collect(Collectors.toList());
    }

    // Endpoint AJAX: retorna ativos em estoque (sem responsável)
    @GetMapping("/ativos-estoque")
    @ResponseBody
    public List<Map<String, Object>> ativosEstoque() {
        List<Asset> ativos = assetRepository.findByResponsavelIsNull();
        return ativos.stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("patrimonio", a.getPatrimonio());
            map.put("tipo", a.getTipo());
            map.put("modelo", a.getModelo());
            return map;
        }).collect(Collectors.toList());
    }

    @PostMapping("/transferir")
    public String transferirAtivo(
            @RequestParam Long assetId,
            @RequestParam(required = false) Long novoResponsavelId,
            RedirectAttributes redirectAttributes) {

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado"));

        // Guarda o nome do responsável antigo
        String nomeAntigo = (asset.getResponsavel() != null)
                ? asset.getResponsavel().getNome()
                : "Estoque";

        // Define o novo responsável
        Employee novoResponsavel = null;
        String nomeNovo = "Estoque";
        if (novoResponsavelId != null) {
            novoResponsavel = employeeRepository.findById(novoResponsavelId)
                    .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
            nomeNovo = novoResponsavel.getNome();
        }

        // Registra no histórico
        AssetHistory historico = new AssetHistory();
        historico.setAsset(asset);
        historico.setDataEvento(LocalDateTime.now());
        historico.setDescricao("Transferência: Saiu de " + nomeAntigo + " para " + nomeNovo);
        historyRepository.save(historico);

        // Atualiza o ativo
        asset.setResponsavel(novoResponsavel);
        asset.setStatus(novoResponsavel != null ? "EM_USO" : "DISPONIVEL");
        assetRepository.save(asset);

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Ativo " + asset.getPatrimonio() + " transferido de " + nomeAntigo + " para " + nomeNovo
                        + " com sucesso!");

        return "redirect:/transferencias";
    }
}
