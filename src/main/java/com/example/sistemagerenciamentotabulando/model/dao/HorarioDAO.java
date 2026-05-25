package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import java.util.List;

public interface HorarioDAO {
    void inserir(Horario h);
    void atualizar(Horario h);
    void deletarPorId(Integer id_horario);
    Horario procurarPorId(Integer id_horario);
    List<Horario> listarTodos();
}
