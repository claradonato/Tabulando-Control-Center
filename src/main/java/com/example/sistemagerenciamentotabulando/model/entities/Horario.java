package com.example.sistemagerenciamentotabulando.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Horario {
    private Integer id_horario;
    private String diaSemana;
    private String turno;
    private String hora; //AB ou CD
    private String nomeMonitor;

    private List<Visitante> frequencia = new ArrayList<>();
    private List<Jogo> jogosUtilizados = new ArrayList<>();

    public Horario(Integer id_horario, String diaSemana, String turno, String hora, String nomeMonitor, List<Visitante> frequencia, List<Jogo> jogosUtilizados) {
        this.id_horario = id_horario;
        this.diaSemana = diaSemana;
        this.turno = turno;
        this.hora = hora;
        this.nomeMonitor = nomeMonitor;
    }

    public Horario(int id_horario, String diaSemana, String turno, String hora, String nomeMonitor) {
        this.id_horario = id_horario;
        this.diaSemana = diaSemana;
        this.hora = hora;
        this.turno = turno;
        this.nomeMonitor = nomeMonitor;
    }

    public Horario(String diaSemana, String turno, String hora, String nomeMonitor) {
        this.diaSemana = diaSemana;
        this.turno = turno;
        this.hora = hora;
        this.nomeMonitor = nomeMonitor;
    }

    public Integer getId_horario() {
        return id_horario;
    }

    public void setId_horario(Integer id_horario) {
        this.id_horario = id_horario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNomeMonitor() {
        return nomeMonitor;
    }

    public void setNomeMonitor(String nomeMonitor) {
        this.nomeMonitor = nomeMonitor;
    }

    public List<Visitante> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<Visitante> frequencia) {
        this.frequencia = frequencia;
    }

    public List<Jogo> getJogosUtilizados() {
        return jogosUtilizados;
    }

    public void setJogosUtilizados(List<Jogo> jogosUtilizados) {
        this.jogosUtilizados = jogosUtilizados;
    }
}
