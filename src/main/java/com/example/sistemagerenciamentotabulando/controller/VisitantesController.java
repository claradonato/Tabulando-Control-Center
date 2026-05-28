package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
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

public class VisitantesController implements Initializable {
    @FXML
    private TableView<Visitante> listagemVisitantes;

    @FXML
    private TableColumn<Visitante, String> colNome;
    @FXML
    private TableColumn<Visitante, String> colGenero;
    @FXML
    private TableColumn<Visitante, Integer> colIdade;
    @FXML
    private TableColumn<Visitante, String> colCurso;
    @FXML
    private TableColumn<Visitante, Void> colAcoes;

    @FXML
    protected void carregarDados(){
        List<Visitante> v = DAOFactory.createVisitanteDAO().listarTodos();
        ObservableList<Visitante> obsVisitantes = FXCollections.observableArrayList(v);
        listagemVisitantes.setItems(obsVisitantes);
    }

    protected void configurarColunas(){
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarDados();
    }

    @FXML
    protected void onNovoVisitanteClicked(){
        Application.abrirNovaJanela("adicionar-visitante-view.fxml");
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarDados();
    }
}
