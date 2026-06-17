package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;

import java.time.LocalDate;
import java.util.List;

public interface HorarioDAO {
    void inserir(Horario h);
    void atualizar(Horario h);
    List<Horario> listarTodos();
    List<String> listarMonitores();

    //métodos de busca para o filtro de horário na tableview
    List<Horario> buscarPorSemana(LocalDate dataReferencia);
    List<Horario> buscarPorMonitor(String monitor);
    List<Horario> buscarPorSemanaEMonitor(LocalDate data, String monitor);
    List<Horario> buscarPorStatus(String status);
    List<Horario> buscarPorSemanaEStatus(LocalDate data, String status);
    List<Horario> buscarPorMonitorEStatus(String monitor, String status);
    List<Horario> buscarPorSemanaMonitorEStatus(LocalDate data, String monitor, String status);

    void adicionarVisitanteHorario(Integer matricula, Integer id_horario);
    void adicionarJogoHorario(Integer idJogo, Integer idHorario);
    List<Visitante> buscarVisitantesHorario(Integer idHorario);
    List<Jogo> buscarJogosHorario(Integer idHorario);
}
