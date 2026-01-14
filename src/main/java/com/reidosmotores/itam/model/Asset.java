package com.reidosmotores.itam.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
@Table(name = "tb_ativos")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String patrimonio; // Ex: RM-001

    private String tipo;       // Ex: Notebook
    private String marca;      // Ex: Dell
    private String modelo;     // Ex: G15
    private String numeroSerie;
    
    private String status;     // Ex: DISPONIVEL, EM_USO

    // DADOS FINANCEIROS
    private LocalDate dataCompra;
    
    @Column(name = "valor_compra", precision = 10, scale = 2)
    private BigDecimal valorCompra; // Ex: 5000.00

    // Relacionamento com Funcionário
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee responsavel;

    /**
     * Calcula o valor atual do ativo baseado na depreciação linear de 5 anos (60 meses).
     * Este método não salva dados no banco, apenas calcula para exibição.
     */
    public BigDecimal getValorAtual() {
        if (dataCompra == null || valorCompra == null) {
            return BigDecimal.ZERO;
        }

        // Calcula meses de uso até hoje
        long mesesDeUso = ChronoUnit.MONTHS.between(dataCompra, LocalDate.now());
        
        // Se passou de 60 meses (5 anos), o valor contábil é zero
        if (mesesDeUso >= 60) {
            return BigDecimal.ZERO;
        }

        // Cálculo da perda: (Valor / 60) * Meses Usados
        BigDecimal depreciacaoMensal = valorCompra.divide(BigDecimal.valueOf(60), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal perdaTotal = depreciacaoMensal.multiply(BigDecimal.valueOf(mesesDeUso));
        
        BigDecimal valorAtual = valorCompra.subtract(perdaTotal);
        
        // Retorna o valor atual, garantindo que não seja negativo
        return valorAtual.max(BigDecimal.ZERO);
    }
}