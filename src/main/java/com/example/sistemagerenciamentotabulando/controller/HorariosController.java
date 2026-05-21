package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HorariosController implements Initializable {
    @FXML
    private TextField idHorario;

    @FXML
    private Label retornoHorario;

    @FXML
    private TableView<Horario> listagemHorarios;

    @FXML
    private TableColumn<Horario, String> colDiaSemana;

    @FXML
    private TableColumn<Horario, String> colTurno;

    @FXML
    private TableColumn<Horario, String> colHora;

    @FXML
    private TableColumn<Horario, Void> colAcoes;

    @FXML
    protected void onBuscarHorarioClicked(){
        Integer id = Integer.parseInt(idHorario.getText());
        Horario h = DAOFactory.createHorarioDAO().procurarPorId(id);
        retornoHorario.setText(" " + h.getDiaSemana() + " " + h.getTurno() + " " + h.getHora() + " " + h.getNomeMonitor());
    }

    @FXML
    protected void onNovoHorarioClicked(){ Application.criarTela("adicionar-horario.fxml");}

    @FXML
    protected void onDashBoardClicked(){
        Application.criarTela("dashboard-view.fxml");
    }

    @FXML
    public void listarHorarios(){
        colDiaSemana.setCellValueFactory(new PropertyValueFactory<>("diaSemana"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));

        // hora + monitor juntos
        colHora.setCellFactory(coluna -> new TableCell<Horario, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){
                    setText(null);
                } else {
                    Horario h = getTableView().getItems().get(getIndex());

                    setText(
                            h.getHora() + "\n" +
                                    h.getNomeMonitor()
                    );

                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        // COLUNA DE AÇÕES
        colAcoes.setCellFactory(coluna -> new TableCell<>() {

            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");

            {
                btnEditar.setStyle("-fx-background-color: #f58220; -fx-text-fill: white;");
                btnExcluir.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                btnEditar.setOnAction(event -> {
                    Horario h = getTableView().getItems().get(getIndex());

                    FXMLLoader loader = Application.trocarTela("exibir-horario-view.fxml", Application.getStagePrincipal());

                    ExibirHorarioController controller = loader.getController();
                    controller.carregarHorario(h);

                    System.out.println("Editar: " + h.getId_horario());
                });

                btnExcluir.setOnAction(event -> {
                    Horario h = getTableView().getItems().get(getIndex());

                    System.out.println("Excluir: " + h.getId_horario());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){
                    setGraphic(null);
                } else {

                    HBox botoes = new HBox(5, btnEditar, btnExcluir);
                    botoes.setAlignment(Pos.CENTER);

                    setGraphic(botoes);
                }
            }
        });

        List<Horario> horarios = DAOFactory.createHorarioDAO().listarTodos();
        ObservableList<Horario> obsHorarios = FXCollections.observableArrayList(horarios);
        listagemHorarios.setItems(obsHorarios);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listarHorarios();
    }
}
