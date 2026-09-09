package com.buscagym.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "planos")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomePlano;       // Ex: Plano Gold, Plano Smart
    private Double precoMensal;     // Ex: 99.90
    private String fidelidade;      // Ex: "12 meses", "Sem fidelidade"
    private String beneficios;      // Ex: "Acesso total, musculação, aulas"
    private boolean destaque;       // Ex: true para o "Mais Vantajoso"

    @ManyToOne
    @JoinColumn(name = "academia_id")
    @JsonIgnore
    private Academia academia;

    // Construtores, Getters e Setters
    public Plano() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }

    public Double getPrecoMensal() { return precoMensal; }
    public void setPrecoMensal(Double precoMensal) { this.precoMensal = precoMensal; }

    public String getFidelidade() { return fidelidade; }
    public void setFidelidade(String fidelidade) { this.fidelidade = fidelidade; }

    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }

    public boolean isDestaque() { return destaque; }
    public void setDestaque(boolean destaque) { this.destaque = destaque; }

    public Academia getAcademia() { return academia; }
    public void setAcademia(Academia academia) { this.academia = academia; }
}