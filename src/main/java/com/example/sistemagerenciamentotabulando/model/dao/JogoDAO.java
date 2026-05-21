package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import java.util.List;

public interface JogoDAO {
    void inserir(Jogo j);
    void atualizar(Jogo j);
    void deletarPorId(Integer id_jogo);
    Jogo procurarPorId(Integer id_jogo);
    List<Jogo> listarTodos();
}
