package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JogosController implements Initializable {
    private static Jogo jogoSelecionado;

    // Atributos e métodos da tela JOGOS-VIEW.FXML -------------------------------------------------------------------------------
    @FXML
    private TextField filtrarId;

    @FXML
    private Label avisoFiltro;

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
        Application.abrirNovaJanela("adicionar-jogo-view.fxml");
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
                    jogoSelecionado = getTableView().getItems().get(getIndex());
                    Application.mudarCena("exibir-jogo-view.fxml");
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
        // jogos-view.fxml
        if(listagemJogos != null){
            configurarColunas();
            configurarColunaAcoes();
            carregarDados();
        }

        // exibir-jogo-view.fxml
        if(titulo != null && jogoSelecionado != null){
            preencherCamposJogo(jogoSelecionado);
        }

        // editar-jogo-view.fxml
        if(tituloEditar != null && jogoSelecionado != null){
            carregarJogoEditar(jogoSelecionado);
        }
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

            Jogo jogo = DAOFactory.createJogoDAO().procurarPorId(id);
            if(jogo != null){
                ObservableList<Jogo> obsJogos = FXCollections.observableArrayList();
                obsJogos.add(jogo);
                listagemJogos.setItems(obsJogos);
                avisoFiltro.setText("");
            } else {
                avisoFiltro.setText("Jogo não encontrado.");
                listagemJogos.setItems(
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

    // Atributos e métodos da tela EXIBIR-JOGO-VIEW.FXML -------------------------------------------------------------------------
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
    public void preencherCamposJogo(Jogo j) {
        titulo.setText("Título: " + j.getTitulo());
        tipo.setText("Tipo: " + j.getTipo());
        numJogadores.setText("Número de jogadores: " + String.valueOf(j.getMinimoNumeroJogadores()) + " - " + String.valueOf(j.getMaximoNumeroJogadores()));
        descricao.setText("Descrição: " + j.getDescricao());
        marca.setText("Marca: " + j.getMarca());
        faixaEtaria.setText("Faixa etária: " + String.valueOf(j.getFaixaEtaria()));
        tempoPartida.setText("Tempo de partida: " + String.valueOf(j.getTempoPartida()));
    }

    @FXML
    protected void onRefreshButtonExibirJogoClicked(){
        preencherCamposJogo(jogoSelecionado);
    }

    @FXML
    protected void onAtualizarJogoClicked(){
        Application.mudarCena("editar-jogo-view.fxml");
    }

    // Atributos e métodos da tela ADICIONAR-JOGO-VIEW.FXML ------------------------------------------------------------------------
    @FXML
    private TextField tituloAdicionar;
    @FXML
    private TextField tipoAdicionar;
    @FXML
    private TextField minJogadores;
    @FXML
    private TextField maxJogadores;
    @FXML
    private TextField descricaoAdicionar;
    @FXML
    private TextField marcaAdicionar;
    @FXML
    private TextField faixaEtariaAdicionar;
    @FXML
    private TextField tempoPartidaAdicionar;

    @FXML
    protected void onSalvarJogoClicked(){
        Jogo j = new Jogo(tituloAdicionar.getText(), tipoAdicionar.getText(), Integer.parseInt(minJogadores.getText()), Integer.parseInt(maxJogadores.getText()), descricaoAdicionar.getText(), marcaAdicionar.getText(), Integer.parseInt(faixaEtariaAdicionar.getText()), Integer.parseInt(tempoPartidaAdicionar.getText()));
        DAOFactory.createJogoDAO().inserir(j);
        tituloAdicionar.setText(" ");
        tipoAdicionar.setText(" ");
        minJogadores.setText(" ");
        maxJogadores.setText(" ");
        descricaoAdicionar.setText(" ");
        marcaAdicionar.setText(" ");
        faixaEtariaAdicionar.setText(" ");
        tempoPartidaAdicionar.setText(" ");
    }

    @FXML
    private Button btnFechar;

    @FXML
    protected void fecharTela() {
        Application.mudarCena("exibir-jogo-view.fxml");
    }

    // Atributos e métodos da tela EDITAR-JOGO-VIEW.FXML --------------------------------------------------------------
    @FXML
    private TextField tituloEditar;
    @FXML
    private TextField tipoEditar;
    @FXML
    private TextField minJogadoresEditar;
    @FXML
    private TextField maxJogadoresEditar;
    @FXML
    private TextField descricaoEditar;
    @FXML
    private TextField marcaEditar;
    @FXML
    private TextField faixaEtariaEditar;
    @FXML
    private TextField tempoPartidaEditar;

    @FXML
    protected void fecharTelaEditar() {
        Application.mudarCena("exibir-jogo-view.fxml");
    }

    @FXML
    protected void carregarJogoEditar(Jogo j){
        tituloEditar.setText(j.getTitulo());
        tipoEditar.setText(j.getTipo());
        minJogadoresEditar.setText(String.valueOf(j.getMinimoNumeroJogadores()));
        maxJogadoresEditar.setText(String.valueOf(j.getMaximoNumeroJogadores()));
        descricaoEditar.setText(j.getDescricao());
        marcaEditar.setText(j.getMarca());
        faixaEtariaEditar.setText(String.valueOf(j.getFaixaEtaria()));
        tempoPartidaEditar.setText(String.valueOf(j.getTempoPartida()));
    }

    @FXML
    protected void onSalvarClicked(){
        jogoSelecionado.setTitulo(tituloEditar.getText());
        jogoSelecionado.setTipo(tipoEditar.getText());
        jogoSelecionado.setMinimoNumeroJogadores(Integer.valueOf(minJogadoresEditar.getText()));
        jogoSelecionado.setMaximoNumeroJogadores(Integer.valueOf(maxJogadoresEditar.getText()));
        jogoSelecionado.setDescricao(descricaoEditar.getText());
        jogoSelecionado.setMarca(marcaEditar.getText());
        jogoSelecionado.setFaixaEtaria(Integer.valueOf(faixaEtariaEditar.getText()));
        jogoSelecionado.setTempoPartida(Integer.valueOf(tempoPartidaEditar.getText()));

        DAOFactory.createJogoDAO().atualizar(jogoSelecionado);
        System.out.println("Atualizado no BD.");

        fecharTela();
    }
}
