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
    List<Horario> buscarPorSemana(LocalDate dataReferencia);

    void adicionarVisitanteHorario(Integer matricula, Integer id_horario);
    void adicionarJogoHorario(Integer idJogo, Integer idHorario);
    List<Visitante> buscarVisitantesHorario(Integer idHorario);
    List<Jogo> buscarJogosHorario(Integer idHorario);
}
