package com.buscagym.api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "academias")
public class Academia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String cidade;
    private String bairro;
    private String imagemUrl;
    private Double notaAvaliacao; // Ex: 4.8

    @OneToMany(mappedBy = "academia", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Plano> planos;

    // Construtores, Getters e Setters
    public Academia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public Double getNotaAvaliacao() { return notaAvaliacao; }
    public void setNotaAvaliacao(Double notaAvaliacao) { this.notaAvaliacao = notaAvaliacao; }

    public List<Plano> getPlanos() { return planos; }
    public void setPlanos(List<Plano> planos) { this.planos = planos; }
}