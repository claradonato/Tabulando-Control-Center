package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JogosController implements Initializable {
    @FXML
    private TableView<Jogo> listagemJogos;

    @FXML
    private TableColumn<Jogo, String> colTitulo;
    @FXML
    private TableColumn<Jogo, String> colTipo;
    @FXML
    private TableColumn<Jogo, Integer> colNumeroMinJogadores;
    @FXML
    private TableColumn<Jogo, Integer> colTempo;
    @FXML
    private TableColumn<Jogo, Void> colAcoes;

    @FXML
    protected void onNovoJogoClicked(){
        Application.criarTela("adicionar-jogo-view.fxml");
    }

    private void carregarDados(){
        List<Jogo> jogos = DAOFactory.createJogoDAO().listarTodos();
        ObservableList<Jogo> obsJogos = FXCollections.observableArrayList(jogos);
        listagemJogos.setItems(obsJogos);
    }

    private void configurarColunas(){
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNumeroMinJogadores.setCellValueFactory(new PropertyValueFactory<>("minimoNumeroJogadores"));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempoPartida"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize jogos");
        configurarColunas();
        carregarDados();
    }
}
