package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarJogoController {
    private Jogo jogo;

    @FXML
    private TextField titulo;

    @FXML
    private TextField tipo;

    @FXML
    private TextField minJogadores;

    @FXML
    private TextField maxJogadores;

    @FXML
    private TextField descricao;

    @FXML
    private TextField marca;

    @FXML
    private TextField faixaEtaria;

    @FXML
    private TextField tempoPartida;

    @FXML
    private Button btnFechar;

    @FXML
    protected void fecharTela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void carregarJogo(Jogo j){
        this.jogo = j;
        titulo.setText(j.getTitulo());
        tipo.setText(j.getTipo());
        minJogadores.setText(String.valueOf(j.getMinimoNumeroJogadores()));
        maxJogadores.setText(String.valueOf(j.getMaximoNumeroJogadores()));
        descricao.setText(j.getDescricao());
        marca.setText(j.getMarca());
        faixaEtaria.setText(String.valueOf(j.getFaixaEtaria()));
        tempoPartida.setText(String.valueOf(j.getTempoPartida()));
    }

    @FXML
    protected void onSalvarClicked(){
        jogo.setTitulo(titulo.getText());
        jogo.setTipo(tipo.getText());
        jogo.setMinimoNumeroJogadores(Integer.valueOf(minJogadores.getText()));
        jogo.setMaximoNumeroJogadores(Integer.valueOf(maxJogadores.getText()));
        jogo.setDescricao(descricao.getText());
        jogo.setMarca(faixaEtaria.getText());
        jogo.setFaixaEtaria(Integer.valueOf(tempoPartida.getText()));
        DAOFactory.createJogoDAO().atualizar(jogo);

        fecharTela();
    }
}
