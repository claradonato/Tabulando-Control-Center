package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

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
    
    private void configurarColunaAcoes(){
        colAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");
            private final Button btnExcluir = new Button("Excluir");
            { //bloco inicializador que roda quando a célula é criada | construtor interno
                btnVer.setStyle("-fx-background-color: #f58220; -fx-text-fill: white;");
                btnExcluir.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                btnVer.setOnAction(event -> {
                    Jogo j = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = Application.trocarTela("exibir-jogo-view.fxml", Application.getStagePrincipal());
                    ExibirJogoController controller = loader.getController();
                    controller.carregarJogo(j);
                });

                btnExcluir.setOnAction(event -> {
                    Jogo j = getTableView().getItems().get(getIndex());
                    DAOFactory.createJogoDAO().deletarPorId(j.getId());
                    carregarDados();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){ //remove os botões de células que estão vazias
                    setGraphic(null);
                } else {
                    HBox botoes = new HBox(5, btnVer, btnExcluir);
                    botoes.setAlignment(Pos.CENTER);
                    setGraphic(botoes);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize jogos");
        configurarColunas();
        configurarColunaAcoes();
        carregarDados();
    }

    @FXML
    protected void onRefreshButtonClicked(){
        System.out.println("Ta fazendo refresh");
        carregarDados();
    }
}
