package com.example.sistemagerenciamentotabulando.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private Integer id;
    private String titulo;
    private String tipo;
    private Integer minimoNumeroJogadores;
    private Integer maximoNumeroJogadores;
    private String descricao;
    private String marca;
    private Integer faixaEtaria;
    private Integer tempoPartida;

    private List<Horario> horariosPresente = new ArrayList<>();

    public Jogo(Integer id, String titulo, String tipo, Integer minimoNumeroJogadores, Integer maximoNumeroJogadores, String descricao, String marca, Integer faixaEtaria, Integer tempoPartida) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.minimoNumeroJogadores = minimoNumeroJogadores;
        this.maximoNumeroJogadores = maximoNumeroJogadores;
        this.descricao = descricao;
        this.marca = marca;
        this.faixaEtaria = faixaEtaria;
        this.tempoPartida = tempoPartida;
    }

    public Jogo(String titulo, String tipo, Integer minimoNumeroJogadores, Integer maximoNumeroJogadores, String descricao, String marca, Integer faixaEtaria, Integer tempoPartida) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.minimoNumeroJogadores = minimoNumeroJogadores;
        this.maximoNumeroJogadores = maximoNumeroJogadores;
        this.descricao = descricao;
        this.marca = marca;
        this.faixaEtaria = faixaEtaria;
        this.tempoPartida = tempoPartida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getMinimoNumeroJogadores() {
        return minimoNumeroJogadores;
    }

    public void setMinimoNumeroJogadores(Integer minimoNumeroJogadores) {
        this.minimoNumeroJogadores = minimoNumeroJogadores;
    }

    public Integer getMaximoNumeroJogadores() {
        return maximoNumeroJogadores;
    }

    public void setMaximoNumeroJogadores(Integer maximoNumeroJogadores) {
        this.maximoNumeroJogadores = maximoNumeroJogadores;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(Integer faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public Integer getTempoPartida() {
        return tempoPartida;
    }

    public void setTempoPartida(Integer tempoPartida) {
        this.tempoPartida = tempoPartida;
    }

    public List<Horario> getHorariosPresente() {
        return horariosPresente;
    }

    public void setHorariosPresente(List<Horario> horariosPresente) {
        this.horariosPresente = horariosPresente;
    }
}
