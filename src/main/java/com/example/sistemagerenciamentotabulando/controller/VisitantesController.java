package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisitantesController implements Initializable {
    private static Visitante visitanteSelecionado;

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
                    visitanteSelecionado = getTableView().getItems().get(getIndex());
                    Application.mudarCena("exibir-visitante-view.fxml");
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
        // visitante-view.fxml
        if(listagemVisitantes != null){
            configurarColunas();
            configurarColunaAcoes();
            carregarDados();
        }
        // exibir-visitante-view.fxml
        if(nome != null && visitanteSelecionado != null){
            carregarVisitante(visitanteSelecionado);
        }

        // editar-cadastro-view.fxml
        if(nomeEditar != null && visitanteSelecionado != null){
            carregarVisitanteEditar(visitanteSelecionado);
        }
    }

    @FXML
    protected void onNovoVisitanteClicked(){
        Application.mudarCena("adicionar-visitante-view.fxml");
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

    // Atributos e métodos da tela EXIBIR-VISITANTE-VIEW.FXML ------------------------------------------------------
    @FXML
    private Label nome;
    @FXML
    private Label matricula;
    @FXML
    private Label idade;
    @FXML
    private Label genero;
    @FXML
    private Label curso;
    @FXML
    private Label turno;
    @FXML
    private Label possuiNEE;

    @FXML
    protected void carregarVisitante(Visitante v){
        nome.setText(v.getNome());
        matricula.setText(String.valueOf(v.getMatricula()));
        idade.setText(String.valueOf(v.getIdade()));
        genero.setText(v.getGenero());
        curso.setText(v.getCurso());
        turno.setText(v.getTurno());
        possuiNEE.setText(String.valueOf(v.getPossuiNEE()));
    }

    @FXML
    protected void onEditarCadastroClicked(){
        Application.mudarCena("editar-cadastro-view.fxml");
    }

    @FXML
    protected void onRefreshButtonExibirClicked(){
        carregarVisitante(visitanteSelecionado);
    }

    //Atributos e métodos da tela ADICIONAR-VISITANTE-VIEW.FXML --------------------------------------------------------
    @FXML
    private TextField nomeAdicionar;
    @FXML
    private TextField matriculaAdicionar;
    @FXML
    private TextField idadeAdicionar;
    @FXML
    private TextField generoAdicionar;
    @FXML
    private TextField nivelEnsinoAdicionar;
    @FXML
    private TextField cursoAdicionar;
    @FXML
    private TextField turnoAdicionar;
    @FXML
    private TextField possuiNEEAdicionar;

    @FXML
    protected void fecharTelaAdicionar() {
        Application.mudarCena("visitantes-view.fxml");
    }

    @FXML
    protected void onSalvarVisitanteClicked(){
        Visitante v = new Visitante((int) Long.parseLong(matriculaAdicionar.getText()), nomeAdicionar.getText(), Integer.parseInt(idadeAdicionar.getText()), generoAdicionar.getText(), nivelEnsinoAdicionar.getText(), cursoAdicionar.getText(), turnoAdicionar.getText(), Boolean.valueOf(possuiNEEAdicionar.getText()));
        DAOFactory.createVisitanteDAO().inserir(v);
        matriculaAdicionar.setText(" ");
        nomeAdicionar.setText(" ");
        idadeAdicionar.setText(" ");
        generoAdicionar.setText(" ");
        nivelEnsinoAdicionar.setText(" ");
        cursoAdicionar.setText(" ");
        turnoAdicionar.setText(" ");
        possuiNEEAdicionar.setText(" ");
        fecharTelaAdicionar();
    }

    // Atributos e métodos da tela EDITAR-VISITANTE-VIEW.FXML -------------------------------------------------------------------
    @FXML
    private TextField nomeEditar;
    @FXML
    private TextField matriculaEditar;
    @FXML
    private TextField idadeEditar;
    @FXML
    private TextField generoEditar;
    @FXML
    private TextField nivelEnsinoEditar;
    @FXML
    private TextField cursoEditar;
    @FXML
    private TextField turnoEditar;
    @FXML
    private TextField possuiNEEEditar;

    @FXML
    protected void fecharTelaEditar() {
        Application.mudarCena("exibir-visitante-view.fxml");
    }

    @FXML
    protected void onSalvarVisitanteEditarClicked(){
        visitanteSelecionado.setNome(nomeEditar.getText());
        visitanteSelecionado.setMatricula(Integer.parseInt(matriculaEditar.getText()));
        visitanteSelecionado.setIdade(Integer.parseInt(idadeEditar.getText()));
        visitanteSelecionado.setGenero(generoEditar.getText());
        visitanteSelecionado.setCurso(cursoEditar.getText());
        visitanteSelecionado.setTurno(turnoEditar.getText());
        visitanteSelecionado.setNivel_ensino(nivelEnsinoEditar.getText());
        visitanteSelecionado.setPossuiNEE(Boolean.valueOf(possuiNEEEditar.getText()));

        DAOFactory.createVisitanteDAO().atualizar(visitanteSelecionado);
        fecharTelaEditar();
    }

    @FXML
    protected void carregarVisitanteEditar(Visitante v){
        nomeEditar.setText(v.getNome());
        matriculaEditar.setText(String.valueOf(v.getMatricula()));
        idadeEditar.setText(String.valueOf(v.getIdade()));
        generoEditar.setText(v.getGenero());
        cursoEditar.setText(v.getCurso());
        turnoEditar.setText(v.getTurno());
        nivelEnsinoEditar.setText(v.getNivel_ensino());
        possuiNEEEditar.setText(String.valueOf(v.getPossuiNEE()));
    }

}
