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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class HorariosController implements Initializable {
    @FXML
    private TextField filtrarId;

    @FXML
    private Label avisoFiltro;

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
    protected void onNovoHorarioClicked(){ Application.mudarCena("adicionar-horario.fxml");}

    @FXML
    protected void onDashBoardClicked(){
        Application.mudarCena("dashboard-view.fxml");
    }

    private void configurarColunas(){
        colDiaSemana.setCellValueFactory(new PropertyValueFactory<>("diaSemana"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
    }

    private void configurarColunaHora(){
        colHora.setCellFactory(coluna -> new TableCell<Horario, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                } else {
                    Horario h = getTableView().getItems().get(getIndex());
                    setText(h.getHora() + "\n" + h.getNomeMonitor());
                }
            }
        });
    }

    private void configurarColunaAcoes(){
        colAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            { //bloco inicializador que roda quando a célula é criada | construtor interno
                btnEditar.setStyle("-fx-background-color: #f58220; -fx-text-fill: white;");
                btnExcluir.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                btnEditar.setOnAction(event -> {
                    Horario h = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = Application.mudarCena("exibir-horario-view.fxml");
                    ExibirHorarioController controller = loader.getController();
                    controller.carregarHorario(h);
                });

                btnExcluir.setOnAction(event -> {
                    Horario h = getTableView().getItems().get(getIndex());
                    DAOFactory.createHorarioDAO().deletarPorId(h.getId_horario());
                    carregarDados();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){ //remove os botões de células que estão vazias
                    setGraphic(null);
                } else {
                    HBox botoes = new HBox(5, btnEditar, btnExcluir);
                    botoes.setAlignment(Pos.CENTER);
                    setGraphic(botoes);
                }
            }
        });
    }

    private void carregarDados(){
        List<Horario> horarios = DAOFactory.createHorarioDAO().listarTodos();
        ObservableList<Horario> obsHorarios = FXCollections.observableArrayList(horarios);
        listagemHorarios.setItems(obsHorarios);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        configurarColunaHora();
        configurarColunaAcoes();
        carregarDados();
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarDados();
    }

    @FXML
    protected void onFiltrarButtonClicked(){
        if(filtrarId.getText() == null || filtrarId.getText().trim().isEmpty()){
            avisoFiltro.setText("Sem filtro.");
        }else{
            Integer id = Integer.parseInt(filtrarId.getText());

            Horario horario = DAOFactory.createHorarioDAO().procurarPorId(id);
            if(horario != null){
                ObservableList<Horario> obsHorarios = FXCollections.observableArrayList();
                obsHorarios.add(horario);
                listagemHorarios.setItems(obsHorarios);
                avisoFiltro.setText("");
            } else {
                avisoFiltro.setText("Horário não encontrado.");
                listagemHorarios.setItems(
                        FXCollections.observableArrayList()
                );
            }
        }
    }

    @FXML
    protected void onLimparFiltroClicked(){
        filtrarId.setText(null);
        avisoFiltro.setText(null);
        carregarDados();
    }
}
