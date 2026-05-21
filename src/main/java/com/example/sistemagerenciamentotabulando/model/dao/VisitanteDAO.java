package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import java.util.List;

public interface VisitanteDAO {
    void inserir(Visitante v);
    void atualizar(Visitante v);
    void deletarPorMatricula(Integer matricula);
    Visitante procurarPorMatricula(Integer matricula);
    List<Visitante> listarTodos();
}
