package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExibirJogoController {
    private Jogo jogo;

    @FXML
    private Label titulo;
    @FXML
    private Label tipo;
    @FXML
    private Label numJogadores;
    @FXML
    private Label descricao;
    @FXML
    private Label marca;
    @FXML
    private Label faixaEtaria;
    @FXML
    private Label tempoPartida;

    @FXML
    public void carregarJogo(Jogo j) {
        this.jogo = j;
        titulo.setText(jogo.getTitulo());
        tipo.setText(jogo.getTipo());
        numJogadores.setText(String.valueOf(jogo.getMinimoNumeroJogadores()));
        descricao.setText(jogo.getDescricao());
        marca.setText(jogo.getMarca());
        faixaEtaria.setText(String.valueOf(jogo.getFaixaEtaria()));
        tempoPartida.setText(String.valueOf(jogo.getTempoPartida()));
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarJogo(jogo);
    }
}
