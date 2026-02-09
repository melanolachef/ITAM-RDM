package com.reidosmotores.itam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data; // Se você usa Lombok (Recomendado)

@Data // O Lombok cria os Getters e Setters automaticamente
@Entity
@Table(name = "tb_linhas")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String operadora;
    private String tipo; // Pré, Pós, Controle
    
    // --- O CAMPO QUE FALTAVA ---
    private String vencimento; // Ex: "Dia 28" ou "01/03"

    @ManyToOne
    private Employee responsavel; // Pode ser null se estiver "Sem Uso"

    // --- GETTERS E SETTERS MANUAIS (Caso seu Lombok não esteja funcionando) ---
    // Se você usa o @Data ali em cima, pode apagar tudo daqui para baixo.
    // Se não usa, mantenha esses métodos:

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getOperadora() { return operadora; }
    public void setOperadora(String operadora) { this.operadora = operadora; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getVencimento() { return vencimento; }
    public void setVencimento(String vencimento) { this.vencimento = vencimento; }

    public Employee getResponsavel() { return responsavel; }
    public void setResponsavel(Employee responsavel) { this.responsavel = responsavel; }
}