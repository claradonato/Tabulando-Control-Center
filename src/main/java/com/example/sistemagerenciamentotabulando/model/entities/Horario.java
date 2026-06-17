package com.example.sistemagerenciamentotabulando.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Horario {
    private Integer id_horario;
    private LocalDate dataHorario;
    private String turno;
    private String hora; //AB ou CD
    private String nomeMonitor;
    private String statusHorario;

    private List<Visitante> frequencia = new ArrayList<>();
    private List<Jogo> jogosUtilizados = new ArrayList<>();

    public Horario(Integer id_horario, LocalDate dataHorario, String turno, String hora, String nomeMonitor, String statusHorario, List<Visitante> frequencia, List<Jogo> jogosUtilizados) {
        this.id_horario = id_horario;
        this.dataHorario = dataHorario;
        this.turno = turno;
        this.hora = hora;
        this.nomeMonitor = nomeMonitor;
        this.statusHorario = statusHorario;
        this.frequencia = frequencia;
        this.jogosUtilizados = jogosUtilizados;
    }

    public Horario(Integer id_horario, LocalDate dataHorario, String turno, String hora, String nomeMonitor, String statusHorario) {
        this(id_horario, dataHorario, turno, hora, nomeMonitor, statusHorario, new ArrayList<>(), new ArrayList<>());
    }

    public Horario(LocalDate dataHorario, String turno, String hora, String nomeMonitor, String statusHorario) {
        this(null, dataHorario, turno, hora, nomeMonitor, statusHorario);
    }

    public Integer getId_horario() {
        return id_horario;
    }

    public void setId_horario(Integer id_horario) {
        this.id_horario = id_horario;
    }

    public LocalDate getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(LocalDate dataHorario) {
        this.dataHorario = dataHorario;
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

    public String getStatusHorario() {return statusHorario;}

    public void setStatusHorario(String statusHorario) {this.statusHorario = statusHorario;}

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
