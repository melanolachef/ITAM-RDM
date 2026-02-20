package com.reidosmotores.itam.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_licencas")
public class SoftwareLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeSoftware; // Ex: Office 365 Business
    private String chaveAtivacao; // Ex: XXXX-YYYY-ZZZZ

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataExpiracao;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Employee colaborador; // Quem está usando a licença (Opcional)

    private String setorResponsavel; // Ex: TI, Financeiro, RH

    // Verifica se vence nos próximos 30 dias
    public boolean isVencendo() {
        if (dataExpiracao == null)
            return false;
        LocalDate hoje = LocalDate.now();
        return !hoje.isAfter(dataExpiracao) && hoje.plusDays(30).isAfter(dataExpiracao);
    }
}