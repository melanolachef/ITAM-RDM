package com.reidosmotores.itam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.reidosmotores.itam.model.Configuracao;
import com.reidosmotores.itam.repository.ConfiguracaoRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ConfiguracaoRepository configRepo;

    @ModelAttribute("globalConfig")
    public Configuracao getGlobalConfig() {
        // Tenta buscar a configuração (ID 1). Se não achar, cria uma nova padrão.
        return configRepo.findById(1L).orElse(new Configuracao());
    }
}