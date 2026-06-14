package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
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

public class VisitantesController implements Initializable {
    @FXML
    private TextField filtrarMatricula;
    @FXML
    private Label avisoFiltro;
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

    private void configurarColunaAcoes(){
        colAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");
            private final Button btnExcluir = new Button("Excluir");
            { //bloco inicializador que roda quando a célula é criada | construtor interno
                btnVer.setStyle("-fx-background-color: #f58220; -fx-text-fill: white;");
                btnExcluir.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                btnVer.setOnAction(event -> {
                    Visitante v = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = Application.mudarCena("exibir-visitante-view.fxml");
                    ExibirVisitanteController controller = loader.getController();
                    controller.carregarVisitante(v);
                });

                btnExcluir.setOnAction(event -> {
                    Visitante v = getTableView().getItems().get(getIndex());
                    DAOFactory.createVisitanteDAO().deletarPorMatricula(v.getMatricula());
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
        configurarColunas();
        configurarColunaAcoes();
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

    @FXML
    protected void onFiltrarButtonClicked(){
        if(filtrarMatricula.getText() == null || filtrarMatricula.getText().trim().isEmpty()){
            avisoFiltro.setText("Sem filtro.");
        }else{
            Integer matricula = Integer.parseInt(filtrarMatricula.getText());

            Visitante visitante = DAOFactory.createVisitanteDAO().procurarPorMatricula(matricula);
            if(visitante != null){
                ObservableList<Visitante> obsVisitantes = FXCollections.observableArrayList();
                obsVisitantes.add(visitante);
                listagemVisitantes.setItems(obsVisitantes);
                avisoFiltro.setText("");
            } else {
                avisoFiltro.setText("Visitante não encontrado.");
                listagemVisitantes.setItems(
                        FXCollections.observableArrayList()
                );
            }
        }
    }

    @FXML
    protected void onLimparFiltroClicked(){
        filtrarMatricula.setText(null);
        avisoFiltro.setText(null);
        carregarDados();
    }
}
