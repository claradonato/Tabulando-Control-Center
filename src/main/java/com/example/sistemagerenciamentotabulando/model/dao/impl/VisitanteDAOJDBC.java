package com.example.sistemagerenciamentotabulando.model.dao.impl;

import com.example.sistemagerenciamentotabulando.model.dao.VisitanteDAO;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;

import java.util.List;

public class VisitanteDAOJDBC implements VisitanteDAO {
    @Override
    public void inserir(Visitante v) {

    }

    @Override
    public void atualizar(Visitante v) {

    }

    @Override
    public void deletarPorMatricula(Integer matricula) {

    }

    @Override
    public Visitante procurarPorMatricula(Integer matricula) {
        return null;
    }

    @Override
    public List<Visitante> listarTodos() {
        return List.of();
    }
}
