package com.example.sistemagerenciamentotabulando.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Visitante {
    private Integer matricula;
    private Integer idade;
    private String curso;

    public Visitante(Integer matricula, Integer idade, String curso, List<Horario> horariosPresente) {
        this.matricula = matricula;
        this.idade = idade;
        this.curso = curso;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
