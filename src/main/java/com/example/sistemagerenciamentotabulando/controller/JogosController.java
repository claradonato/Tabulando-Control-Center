package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class JogosController implements Initializable {
    private static Jogo jogoSelecionado;

    // Atributos e métodos da tela JOGOS-VIEW.FXML -------------------------------------------------------------------------------
    @FXML
    protected void onDashBoardClicked(){
        Application.mudarCena("dashboard-view.fxml");
    }
    @FXML
    protected void onVisitantesClicked(){ Application.mudarCena("jogos-view.fxml");}
    @FXML
    protected void onHorariosClicked() { Application.mudarCena("horarios-view.fxml");}

    @FXML
    private TextField filtrarTitulo;
    @FXML
    private TextField filtrarNumJogadores;
    @FXML
    private ComboBox<String> filtrarDisponibilidade;

    @FXML
    private Label avisoFiltro;

    @FXML
    private TableView<Jogo> listagemJogos;

    @FXML
    private TableColumn<Jogo, String> colTitulo;
    @FXML
    private TableColumn<Jogo, String> colDisponibilidade;
    @FXML
    private TableColumn<Jogo, Integer> colNumeroMinJogadores;
    @FXML
    private TableColumn<Jogo, String> colInformacoes;
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

        colDisponibilidade.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                } else {
                    Jogo j = getTableView().getItems().get(getIndex());
                    setText(j.getDisponibilidade() ? "Sim" : "Não");
                }
            }
        });

        colNumeroMinJogadores.setCellValueFactory(new PropertyValueFactory<>("minimoNumeroJogadores"));
    }

    private void configurarColunaInformacoes(){
        colInformacoes.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                } else {
                    Jogo j = getTableView().getItems().get(getIndex());
                    setText(j.getTipo() + "\n" + j.getMarca()+ "\n" + j.getFaixaEtaria() + "+");
                }
            }
        });
    }
    
    private void configurarColunaAcoes(){
        colAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");
            private final Button btnEditar = new Button("Editar");
            { //bloco inicializador que roda quando a célula é criada | construtor interno
                btnVer.setStyle("-fx-background-color: #f58220; -fx-text-fill: white;");
                btnEditar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                btnVer.setOnAction(event -> {
                    jogoSelecionado = getTableView().getItems().get(getIndex());
                    Application.mudarCena("exibir-jogo-view.fxml");
                });

                btnEditar.setOnAction(event -> {
                    Jogo j = getTableView().getItems().get(getIndex());
                    Application.abrirModal("editar-jogo-view.fxml");
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){ //remove os botões de células que estão vazias
                    setGraphic(null);
                } else {
                    HBox botoes = new HBox(5, btnVer, btnEditar);
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
            configurarColunaInformacoes();
            configurarColunaAcoes();
            // carregar comboBoxes
            filtrarDisponibilidade.getItems().addAll("Disponível", "Indisponível");
            carregarDados();
        }

        if(tipoAdicionar != null){
            tipoAdicionar.getItems().addAll("Estratégia", "Cooperativo", "PartyGame", "Wargame", "Dedução/Blefe", "RPG", "Destreza");
            marcaAdicionar.getItems().addAll("Galápagos", "Devir Brasil", "Grok", "PaperGames", "Estrela");
            disponibilidadeAdicionar.getItems().addAll("Disponível", "Indisponível");
        }

        // exibir-jogo-view.fxml
        if(titulo != null && jogoSelecionado != null){
            preencherCamposJogo(jogoSelecionado);
        }

        // editar-jogo-view.fxml
        if(tituloEditar != null && jogoSelecionado != null){
            tipoEditar.getItems().addAll("Estratégia", "Cooperativo", "PartyGame", "Wargame", "Dedução/Blefe", "RPG", "Destreza");
            marcaEditar.getItems().addAll("Galápagos", "Devir Brasil", "Grok", "PaperGames", "Estrela");
            disponibilidadeEditar.getItems().addAll("Disponível", "Indisponível");
            System.out.println("Chamando preencherCamposEdicao");
            carregarJogoEditar(jogoSelecionado);
        }
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarDados();
    }

    @FXML
    protected void onFiltrarButtonClicked(){
        String titulo = filtrarTitulo.getText().trim();
        String qtdTexto = filtrarNumJogadores.getText();
        String disponibilidadeTexto = filtrarDisponibilidade.getValue();
        boolean temQtd = qtdTexto != null && !qtdTexto.trim().isEmpty();
        boolean temDisponibilidade = disponibilidadeTexto != null && !disponibilidadeTexto.trim().isEmpty();
        boolean temTitulo = titulo != null && !titulo.trim().isEmpty();

        Integer qtd = null;
        if(temQtd){
            try{
                qtd = Integer.parseInt(qtdTexto);
            }
            catch(NumberFormatException e){
                avisoFiltro.setText("Quantidade inválida.");
                return;
            }
        }

        Boolean disponibilidade = null;
        if(temDisponibilidade){
            disponibilidade = disponibilidadeTexto.equals("Disponível");
        }

        List<Jogo> jogos;

        if(temTitulo && temQtd && temDisponibilidade){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorTituloNumJogadoresEDisponibilidade(titulo, qtd, disponibilidade);
        }
        else if(temTitulo && temQtd){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorTituloENumJogadores(titulo, qtd);
        }
        else if(temTitulo && temDisponibilidade){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorTituloEDisponibilidade(titulo, disponibilidade);
        }
        else if(temQtd && temDisponibilidade){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorNumJogadoresEDisponibilidade(qtd, disponibilidade);
        }
        else if(temTitulo){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorTitulo(titulo);
        }
        else if(temQtd){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorNumJogadores(qtd);
        }
        else if(temDisponibilidade){
            jogos = DAOFactory.createJogoDAO().buscarJogoPorDisponibilidade(disponibilidade);
        }
        else{
            jogos = DAOFactory.createJogoDAO().listarTodos();
        }

        listagemJogos.setItems(FXCollections.observableArrayList(jogos));
        avisoFiltro.setText(jogos.isEmpty() ? "Nenhum jogo encontrado." : "");
    }

    @FXML
    protected void onLimparFiltroClicked(){
        filtrarTitulo.setText("");
        filtrarNumJogadores.setText("");
        filtrarDisponibilidade.setValue(null);
        avisoFiltro.setText(null);
        carregarDados();
    }

    @FXML
    protected void onVoltarClicked(){
        Application.mudarCena("jogos-view.fxml");
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
    private Label disponibilidade;

    @FXML
    public void preencherCamposJogo(Jogo j) {
        titulo.setText("Título: " + j.getTitulo());
        tipo.setText("Tipo: " + j.getTipo());
        numJogadores.setText("Número de jogadores: " + String.valueOf(j.getMinimoNumeroJogadores()) + " - " + String.valueOf(j.getMaximoNumeroJogadores()));
        descricao.setText("Descrição: " + j.getDescricao());
        marca.setText("Marca: " + j.getMarca());
        faixaEtaria.setText("Faixa etária: " + String.valueOf(j.getFaixaEtaria()));
        tempoPartida.setText("Tempo de partida: " + String.valueOf(j.getTempoPartida()));
        disponibilidade.setText("Disponibilidade: " + String.valueOf(j.getDisponibilidade()));
    }

    // Atributos e métodos da tela ADICIONAR-JOGO-VIEW.FXML ------------------------------------------------------------------------
    @FXML
    private TextField tituloAdicionar;
    @FXML
    private ComboBox<String> tipoAdicionar;
    @FXML
    private TextField minJogadores;
    @FXML
    private TextField maxJogadores;
    @FXML
    private TextField descricaoAdicionar;
    @FXML
    private ComboBox<String> marcaAdicionar;
    @FXML
    private TextField faixaEtariaAdicionar;
    @FXML
    private TextField tempoPartidaAdicionar;
    @FXML
    private ComboBox<String> disponibilidadeAdicionar;

    @FXML
    protected void onSalvarJogoClicked(){
        Jogo j = new Jogo(tituloAdicionar.getText(), tipoAdicionar.getValue(), Integer.parseInt(minJogadores.getText()), Integer.parseInt(maxJogadores.getText()), descricaoAdicionar.getText(), marcaAdicionar.getValue(), Integer.parseInt(faixaEtariaAdicionar.getText()), Integer.parseInt(tempoPartidaAdicionar.getText()), Boolean.valueOf(disponibilidadeAdicionar.getValue()));
        DAOFactory.createJogoDAO().inserir(j);
        tituloAdicionar.setText(" ");
        tipoAdicionar.setValue(" ");
        minJogadores.setText(" ");
        maxJogadores.setText(" ");
        descricaoAdicionar.setText(" ");
        marcaAdicionar.setValue(" ");
        faixaEtariaAdicionar.setText(" ");
        tempoPartidaAdicionar.setText(" ");
        disponibilidadeAdicionar.setValue(" ");
    }

    @FXML
    protected void fecharTela() {
        // configuração para fechar a telinha
    }

    // Atributos e métodos da tela EDITAR-JOGO-VIEW.FXML --------------------------------------------------------------
    @FXML
    private TextField tituloEditar;
    @FXML
    private ComboBox<String> tipoEditar;
    @FXML
    private TextField minJogadoresEditar;
    @FXML
    private TextField maxJogadoresEditar;
    @FXML
    private TextField descricaoEditar;
    @FXML
    private ComboBox<String> marcaEditar;
    @FXML
    private TextField faixaEtariaEditar;
    @FXML
    private TextField tempoPartidaEditar;
    @FXML
    private ComboBox<String> disponibilidadeEditar;

    @FXML
    protected void fecharTelaEditar() {
        Application.mudarCena("exibir-jogo-view.fxml");
    }

    @FXML
    protected void carregarJogoEditar(Jogo j){
        tituloEditar.setText(j.getTitulo());
        tipoEditar.setValue(j.getTipo());
        minJogadoresEditar.setText(String.valueOf(j.getMinimoNumeroJogadores()));
        maxJogadoresEditar.setText(String.valueOf(j.getMaximoNumeroJogadores()));
        descricaoEditar.setText(j.getDescricao());
        marcaEditar.setValue(j.getMarca());
        faixaEtariaEditar.setText(String.valueOf(j.getFaixaEtaria()));
        tempoPartidaEditar.setText(String.valueOf(j.getTempoPartida()));
        disponibilidadeEditar.setValue(j.getDisponibilidade() ? "Disponível" : "Indisponível");
    }

    @FXML
    protected void onSalvarClicked(){
        jogoSelecionado.setTitulo(tituloEditar.getText());
        jogoSelecionado.setTipo(tipoEditar.getValue());
        jogoSelecionado.setMinimoNumeroJogadores(Integer.valueOf(minJogadoresEditar.getText()));
        jogoSelecionado.setMaximoNumeroJogadores(Integer.valueOf(maxJogadoresEditar.getText()));
        jogoSelecionado.setDescricao(descricaoEditar.getText());
        jogoSelecionado.setMarca(marcaEditar.getValue());
        jogoSelecionado.setFaixaEtaria(Integer.valueOf(faixaEtariaEditar.getText()));
        jogoSelecionado.setTempoPartida(Integer.valueOf(tempoPartidaEditar.getText()));
        jogoSelecionado.setDisponibilidade(disponibilidadeEditar.getValue().equals("Disponível"));

        DAOFactory.createJogoDAO().atualizar(jogoSelecionado);
        System.out.println("Atualizado no BD.");

        fecharTela();
    }
}
