package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarJogoController {
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
    protected void onSalvarJogoClicked(){
        Jogo j = new Jogo(titulo.getText(), tipo.getText(), Integer.parseInt(minJogadores.getText()), Integer.parseInt(maxJogadores.getText()), descricao.getText(), marca.getText(), Integer.parseInt(faixaEtaria.getText()), Integer.parseInt(tempoPartida.getText()));
        DAOFactory.createJogoDAO().inserir(j);
        titulo.setText(" ");
        tipo.setText(" ");
        minJogadores.setText(" ");
        maxJogadores.setText(" ");
        descricao.setText(" ");
        marca.setText(" ");
        faixaEtaria.setText(" ");
        tempoPartida.setText(" ");
    }

    @FXML
    private Button btnFechar;

    @FXML
    protected void fecharTela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
}
