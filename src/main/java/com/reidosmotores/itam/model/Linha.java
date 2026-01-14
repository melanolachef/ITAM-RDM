package com.reidosmotores.itam.model;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
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
@Table(name = "tb_linhas")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    private String operadora;
    private String tipo; // "PRE" ou "POS"

    // Usado APENAS para Pré-Pago (Data exata que expira)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataRecarga; 

    // Usado APENAS para Pós-Pago (Dia fixo do mês, ex: 10)
    private Integer diaVencimento;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Employee responsavel;
    
    // Lógica Inteligente de Alerta
    public boolean isAlertaRecarga() {
        LocalDate hoje = LocalDate.now();

        // Lógica para PRÉ-PAGO (Data exata)
        if ("PRE".equals(tipo) && dataRecarga != null) {
            LocalDate limiteAlerta = hoje.plusDays(5); // Avisa 5 dias antes
            // Retorna true se a data já passou ou está chegando
            return !dataRecarga.isAfter(limiteAlerta);
        }
        
        // Lógica para PÓS-PAGO (Todo mês)
        if ("POS".equals(tipo) && diaVencimento != null) {
            try {
                // Cria a data de vencimento deste mês atual
                YearMonth mesAtual = YearMonth.now();
                // Ajusta caso o dia escolhido (ex: 31) não exista no mês (ex: Fev)
                int diaValido = Math.min(diaVencimento, mesAtual.lengthOfMonth());
                
                LocalDate vencimentoDesteMes = mesAtual.atDay(diaValido);

                // Se já passou o dia deste mês, olhamos para o mês que vem? 
                // Para simplificar: Avisa se hoje está entre 5 dias antes do vencimento
                LocalDate inicioAlerta = vencimentoDesteMes.minusDays(5);
                
                // Se hoje for >= (dia - 5) E hoje <= dia
                return !hoje.isBefore(inicioAlerta) && !hoje.isAfter(vencimentoDesteMes);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}