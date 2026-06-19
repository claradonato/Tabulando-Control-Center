package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import java.util.List;

public interface JogoDAO {
    void inserir(Jogo j);
    void atualizar(Jogo j);
    List<Jogo> buscarJogoPorTitulo(String titulo);
    List<Jogo> buscarJogoPorNumJogadores(Integer quantidade);
    List<Jogo> buscarJogoPorDisponibilidade(Boolean disponibilidade);
    List<Jogo> buscarJogoPorTituloENumJogadores(String titulo, Integer quantidade);
    List<Jogo> buscarJogoPorTituloEDisponibilidade(String titulo, Boolean disponibilidade);
    List<Jogo> buscarJogoPorNumJogadoresEDisponibilidade(Integer quantidade, Boolean disponibilidade);
    List<Jogo> buscarJogoPorTituloNumJogadoresEDisponibilidade(String titulo, Integer quantidade, Boolean disponibilidade);
    List<Jogo> listarTodos();
}
