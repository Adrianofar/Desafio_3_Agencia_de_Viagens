package com.agenciaviagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity  // Isso aqui diz pro JPA que essa classe é uma tabela
@Table(name = "destinos")  // Nome da tabela no banco
public class Destino {
    
    @Id  // Chave primária
    private String id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)  // Coluna não pode ser nula
    private String nome;
    
    @NotBlank(message = "Localização é obrigatória")
    @Column(nullable = false)
    private String localizacao;
    
    @Column(length = 1000)  // Descrição pode ser grande
    private String descricao;
    
    @Column(name = "nota_media")  // Nome da coluna diferente do atributo
    private double notaMedia;
    
    @Column(name = "total_avaliacoes")
    private int totalAvaliacoes;
    
    // Construtor vazio (JPA precisa disso)
    public Destino() {
        this.id = UUID.randomUUID().toString();
        this.notaMedia = 0.0;
        this.totalAvaliacoes = 0;
    }
    
    public Destino(String nome, String localizacao, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.notaMedia = 0.0;
        this.totalAvaliacoes = 0;
    }
    
    // Getters e Setters (mesmos de antes)
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }
    
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getNotaMedia() {
        return notaMedia;
    }
    
    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }
    
    public int getTotalAvaliacoes() {
        return totalAvaliacoes;
    }
    
    public void setTotalAvaliacoes(int totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }
    
    // Método pra calcular a média quando recebe avaliação
    public void adicionarAvaliacao(int novaAvaliacao) {
        double soma = (this.notaMedia * this.totalAvaliacoes) + novaAvaliacao;
        this.totalAvaliacoes++;
        this.notaMedia = Math.round((soma / this.totalAvaliacoes) * 100.0) / 100.0;
    }
}