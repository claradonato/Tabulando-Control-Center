package com.example.sistemagerenciamentotabulando.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Visitante {
    private Integer matricula;
    private String nome;
    private Integer idade;
    private String genero;
    private String nivel_ensino;
    private String curso;
    private String turno;
    private Boolean possuiNEE;

    private List<Visitante> frequencia = new ArrayList<>();

    public Visitante(Integer matricula, String nome, Integer idade, String genero, String nivel_ensino, String curso, String turno, Boolean possuiNEE, List<Visitante> frequencia) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.nivel_ensino = nivel_ensino;
        this.curso = curso;
        this.turno = turno;
        this.possuiNEE = possuiNEE;
        this.frequencia = frequencia;
    }

    public Visitante(Integer matricula, String nome, Integer idade, String genero, String nivel_ensino, String curso, String turno, Boolean possuiNEE) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.nivel_ensino = nivel_ensino;
        this.curso = curso;
        this.turno = turno;
        this.possuiNEE = possuiNEE;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNivel_ensino() {
        return nivel_ensino;
    }

    public void setNivel_ensino(String nivel_ensino) {
        this.nivel_ensino = nivel_ensino;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Boolean getPossuiNEE() {
        return possuiNEE;
    }

    public void setPossuiNEE(Boolean possuiNEE) {
        this.possuiNEE = possuiNEE;
    }

    public List<Visitante> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<Visitante> frequencia) {
        this.frequencia = frequencia;
    }
}
