package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;

import java.util.List;

public interface HorarioDAO {
    void inserir(Horario h);
    void atualizar(Horario h);
    void deletarPorId(Integer id_horario);
    Horario procurarPorId(Integer id_horario);
    List<Horario> listarTodos();

    void adicionarVisitanteHorario(Integer matricula, Integer id_horario);
    void adicionarJogoHorario(Integer idJogo, Integer idHorario);
    List<Visitante> buscarVisitantesHorario(Integer idHorario);
    List<Jogo> buscarJogosHorario(Integer idHorario);
}
