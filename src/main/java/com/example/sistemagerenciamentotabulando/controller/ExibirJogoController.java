package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        titulo.setText("Título: " + jogo.getTitulo());
        tipo.setText("Tipo: " + jogo.getTipo());
        numJogadores.setText("Número de jogadores: " + String.valueOf(jogo.getMinimoNumeroJogadores()) + " - " + String.valueOf(jogo.getMaximoNumeroJogadores()));
        descricao.setText("Descrição: " + jogo.getDescricao());
        marca.setText("Marca: " + jogo.getMarca());
        faixaEtaria.setText("Faixa etária: " + String.valueOf(jogo.getFaixaEtaria()));
        tempoPartida.setText("Tempo de partida: " + String.valueOf(jogo.getTempoPartida()));
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarJogo(this.jogo);
    }

    @FXML
    protected void onAtualizarJogoClicked(){
        FXMLLoader loader = Application.abrirNovaJanela("editar-jogo-view.fxml");
        EditarJogoController controller = loader.getController();
        controller.carregarJogo(jogo);
    }
}
