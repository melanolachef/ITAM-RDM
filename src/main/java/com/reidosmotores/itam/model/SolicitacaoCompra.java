package com.reidosmotores.itam.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_solicitacoes_compras")
public class SolicitacaoCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String software;       // Ex: Adobe Creative Cloud
    private String solicitante;    // Ex: Marketing / João
    private String tipo;           // Ex: Assinatura Mensal, Licença Perpétua
    private String prioridade;     // Baixa, Média, Alta
    
    @Column(length = 500)
    private String justificativa;  // Por que precisamos disso?

    private String status;         // PENDENTE, EM_COTACAO, APROVADO, CONCLUIDO
    private LocalDate dataSolicitacao = LocalDate.now();

    // Método visual para o status
    public String getStatusColor() {
        switch (status) {
            case "PENDENTE": return "bg-warning text-dark";
            case "EM_COTACAO": return "bg-info text-dark";
            case "APROVADO": return "bg-primary";
            case "CONCLUIDO": return "bg-success";
            default: return "bg-secondary";
        }
    }
}