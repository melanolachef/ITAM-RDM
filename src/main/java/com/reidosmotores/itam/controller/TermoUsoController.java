package com.reidosmotores.itam.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.reidosmotores.itam.model.Asset;
import com.reidosmotores.itam.model.Employee;
import com.reidosmotores.itam.model.TermoUso;
import com.reidosmotores.itam.repository.AssetRepository;
import com.reidosmotores.itam.repository.EmployeeRepository;
import com.reidosmotores.itam.repository.TermoUsoRepository;

@Controller
@RequestMapping("/termos")
public class TermoUsoController {

    @Autowired
    private TermoUsoRepository repository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaTermos", repository.findAllByOrderByDataUploadDesc());
        model.addAttribute("listaAtivos", assetRepository.findAll());
        model.addAttribute("listaFuncionarios", employeeRepository.findAll());
        return "termos";
    }

    @PostMapping("/upload")
    public String upload(
            @RequestParam("assetId") Long assetId,
            @RequestParam("funcionarioId") Long funcionarioId,
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam(value = "observacao", required = false) String observacao) {
        try {
            Asset asset = assetRepository.findById(assetId)
                    .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado"));
            Employee funcionario = employeeRepository.findById(funcionarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

            TermoUso termo = new TermoUso();
            termo.setAsset(asset);
            termo.setFuncionario(funcionario);
            termo.setNomeArquivo(arquivo.getOriginalFilename());
            termo.setContentType(arquivo.getContentType());
            termo.setDados(arquivo.getBytes());
            termo.setDataUpload(LocalDateTime.now());
            termo.setObservacao(observacao);

            repository.save(termo);
        } catch (Exception e) {
            // redirect back on error
        }
        return "redirect:/termos";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        TermoUso termo = repository.findById(id).orElse(null);
        if (termo == null || termo.getDados() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(termo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + termo.getNomeArquivo() + "\"")
                .body(termo.getDados());
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/termos";
    }
}
