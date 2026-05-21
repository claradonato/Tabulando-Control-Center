package com.example.sistemagerenciamentotabulando.model.dao.impl;

import com.example.sistemagerenciamentotabulando.model.dao.JogoDAO;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;

import java.util.List;

public class JogoDAOJDBC implements JogoDAO {
    @Override
    public void inserir(Jogo j) {

    }

    @Override
    public void atualizar(Jogo j) {

    }

    @Override
    public void deletarPorId(Integer id_jogo) {

    }

    @Override
    public Jogo procurarPorId(Integer id_jogo) {
        return null;
    }

    @Override
    public List<Jogo> listarTodos() {
        return List.of();
    }
}
