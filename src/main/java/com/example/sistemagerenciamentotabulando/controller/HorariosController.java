package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.time.format.TextStyle;
import java.util.Locale;

public class HorariosController implements Initializable {
    private static Horario horarioSelecionado;

    // TELA DE HORARIOS-VIEW.FXML ---------------------------------------------------------------------------
    //Métodos do Menu
    @FXML
    protected void onDashBoardClicked(){
        Application.mudarCena("dashboard-view.fxml");
    }
    @FXML
    protected void onJogosClicked(){ Application.mudarCena("jogos-view.fxml");}
    @FXML
    protected void onVisitantesClicked() { Application.mudarCena("visitantes-view.fxml");}

    //Atributos do filtro, tabela e botões
    @FXML
    private DatePicker filtroData;
    @FXML
    private ComboBox<String> filtroMonitor;
    @FXML
    private ComboBox<String> filtroStatus;
    @FXML
    private Label avisoFiltro;
    @FXML
    private TableView<Horario> listagemHorarios;
    @FXML
    private TableColumn<Horario, LocalDate> colData; //verifique se o uso de "Date" na coluna está correto
    @FXML
    private TableColumn<Horario, String> colHorario;
    @FXML
    private TableColumn<Horario, String> colMonitor;
    @FXML
    private TableColumn<Horario, Void> colOpcoes;

    @FXML
    protected void onNovoHorarioClicked(){ Application.abrirModal("adicionar-horario.fxml");}

    // configura colunas simples (Data e Monitor)
    private void configurarColunas(){
        colData.setCellValueFactory(new PropertyValueFactory<>("dataHorario"));
        colMonitor.setCellValueFactory(new PropertyValueFactory<>("nomeMonitor"));
    }

    // configura a coluna horario que retorna 3 informações juntas
    private void configurarColunaHorario(){
        colHorario.setCellFactory(coluna -> new TableCell<>() {
            @Override 
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                } else {
                    Horario h = getTableView().getItems().get(getIndex());
                    String diaSemana = h.getDataHorario().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
                    setText(diaSemana + "\n" + h.getTurno() + "\n" + h.getHora());
                }
            }
        });
    }

    // configura a coluna de opções para cada horário (Ver e Editar)
    private void configurarColunaOpcoes(){
        colOpcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnVer = new Button("Ver");
            { //bloco inicializador que roda quando a célula é criada | construtor interno
                btnEditar.setStyle("-fx-background-color: #f58220; -fx-text-fill: white;");
                btnVer.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                btnEditar.setOnAction(event -> {
                    horarioSelecionado = getTableView().getItems().get(getIndex());
                    Application.abrirModal("editar-horario-view.fxml");
                });

                btnVer.setOnAction(event -> {
                    horarioSelecionado = getTableView().getItems().get(getIndex());
                    Application.mudarCena("exibir-horario-visitantes-view.fxml");
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){ //remove os botões de células que estão vazias
                    setGraphic(null);
                } else {
                    HBox botoes = new HBox(5, btnEditar, btnVer);
                    botoes.setAlignment(Pos.CENTER);
                    setGraphic(botoes);
                }
            }
        });
    }

    private void carregarMonitores(){
        List<String> monitores = DAOFactory.createHorarioDAO().listarMonitores();
        filtroMonitor.setItems(FXCollections.observableArrayList(monitores));
    }

    private void carregarFiltroStatus() {
        filtroStatus.getItems().addAll("Planejado", "Concluido", "Cancelado");
    }

    @FXML
    protected void onFiltrarButtonClicked(){
        LocalDate dataSelecionada = filtroData.getValue();
        String monitor = filtroMonitor.getSelectionModel().getSelectedItem();
        String status = filtroStatus.getSelectionModel().getSelectedItem();

        List<Horario> resultado = List.of();

        // Nenhum filtro
        if(dataSelecionada == null && monitor == null && status == null){
            avisoFiltro.setText("Sem filtros.");
            return;
        }

        // Apenas data
        else if(dataSelecionada != null && monitor == null && status == null){
            resultado = DAOFactory.createHorarioDAO().buscarPorSemana(dataSelecionada);
        }

        // Apenas monitor
        else if(dataSelecionada == null && monitor != null && status == null){
            resultado = DAOFactory.createHorarioDAO().buscarPorMonitor(monitor);
        }

        // Data e monitor
        else if (dataSelecionada != null && monitor != null && status == null){
            resultado = DAOFactory.createHorarioDAO().buscarPorSemanaEMonitor(dataSelecionada, monitor);
        }

        // Data e status
        else if (dataSelecionada != null && monitor == null && status != null){
            resultado = DAOFactory.createHorarioDAO().buscarPorSemanaEStatus(dataSelecionada, status);
        }

        // Monitor e status
        else if (dataSelecionada == null && monitor != null && status != null){
            resultado = DAOFactory.createHorarioDAO().buscarPorMonitorEStatus(monitor, status);
        }

        // Monitor, data e status
        else if (dataSelecionada != null && monitor != null && status != null){
            resultado = DAOFactory.createHorarioDAO().buscarPorSemanaMonitorEStatus(dataSelecionada, monitor, status);
        }

        listagemHorarios.setItems(FXCollections.observableArrayList(resultado));
        avisoFiltro.setText(resultado.isEmpty() ? "Nenhum horário encontrado." : "");
    }

    private void carregarDados(){
        List<Horario> horarios = DAOFactory.createHorarioDAO().listarTodos();
        ObservableList<Horario> obsHorarios = FXCollections.observableArrayList(horarios);
        listagemHorarios.setItems(obsHorarios);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tela de listagem
        if (listagemHorarios != null) {
            configurarColunas();
            configurarColunaHorario();
            configurarColunaOpcoes();
            carregarMonitores();
            carregarFiltroStatus();
            carregarDados();
        }

        // Tela de exibição com visitantes
        if(visitantesHorario != null && horarioSelecionado != null){
            descricaoHorario.setText(horarioSelecionado.getDataHorario() + " - " + horarioSelecionado.getHora());
            descricaoNomeMonitor.setText(horarioSelecionado.getNomeMonitor());
            statusHorario.setText(horarioSelecionado.getStatusHorario());
            carregarTabelaVisitantes();
        }

        // Tela de exibição com jogos
        if(jogosHorario != null && horarioSelecionado != null){
            descricaoHorario.setText(horarioSelecionado.getDataHorario() + " - " + horarioSelecionado.getHora());
            descricaoNomeMonitor.setText(horarioSelecionado.getNomeMonitor());
            statusHorario.setText(horarioSelecionado.getStatusHorario());
            carregarTabelaJogos();
        }

        // Tela de editar horario
        if(dataHorarioEditar != null && horarioSelecionado != null){
            preencherCamposEdicao(horarioSelecionado);
        }
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarDados();
    }

    @FXML
    protected void onLimparFiltroClicked(){
        filtroData.setValue(null);
        filtroMonitor.setValue(null);
        filtroStatus.setValue(null);
        avisoFiltro.setText(null);
        carregarDados();
    }

    //Atributos e métodos de ADICIONAR HORÁRIO ---------------------------------------------
    @FXML
    private DatePicker dataHorarioAdicionar;
    @FXML
    private TextField turnoAdicionar;
    @FXML
    private TextField horaAdicionar;
    @FXML
    private TextField nomeMonitorAdicionar;
    @FXML
    private TextField statusHorarioAdicionar;

    @FXML
    protected void fecharTelaAdicionar() {
        Application.mudarCena("horarios-view.fxml");
    }

    @FXML
    protected void onAdcHorarioClicked(){
        Horario h = new Horario(dataHorarioAdicionar.getValue(), turnoAdicionar.getText(), horaAdicionar.getText(), nomeMonitorAdicionar.getText(), statusHorarioAdicionar.getText());
        DAOFactory.createHorarioDAO().inserir(h);
        dataHorarioAdicionar.setValue(null);
        turnoAdicionar.setText(" ");
        horaAdicionar.setText(" ");
        nomeMonitorAdicionar.setText(" ");
        statusHorarioAdicionar.setText(" ");

        Application.mudarCena("horarios-view.fxml");
    }

    //Atributos e métodos de EDITAR HORARIO --------------------------------------------------------
    @FXML
    private DatePicker dataHorarioEditar;
    @FXML
    private TextField turnoEditar;
    @FXML
    private TextField horaEditar;
    @FXML
    private TextField nomeMonitorEditar;
    @FXML
    private TextField statusHorarioEditar;

    @FXML
    private Button btnSalvarFechar;

    @FXML
    private Button btnHabilitarEdicao;

    @FXML
    private void onHabilitarEdicaoClicked(){
        dataHorarioEditar.setDisable(false);
        turnoEditar.setDisable(false);
        horaEditar.setDisable(false);
        nomeMonitorEditar.setDisable(false);
        statusHorarioEditar.setDisable(false);
        btnSalvarFechar.setDisable(false);
        btnHabilitarEdicao.setText("Editando...");
        btnHabilitarEdicao.setDisable(true);
    }

    @FXML
    protected void fecharTelaEditar() {
        Application.mudarCena("horarios-view.fxml");
    }

    private void preencherCamposEdicao(Horario h){
        dataHorarioEditar.setValue(h.getDataHorario());
        turnoEditar.setText(h.getTurno());
        horaEditar.setText(h.getHora());
        nomeMonitorEditar.setText(h.getNomeMonitor());
        statusHorarioEditar.setText(h.getStatusHorario());
    }

    @FXML
    protected void onSalvarClicked(){
        horarioSelecionado.setDataHorario(dataHorarioEditar.getValue());
        horarioSelecionado.setTurno(turnoEditar.getText());
        horarioSelecionado.setHora(horaEditar.getText());
        horarioSelecionado.setNomeMonitor(nomeMonitorEditar.getText());
        horarioSelecionado.setStatusHorario(statusHorarioEditar.getText());
        DAOFactory.createHorarioDAO().atualizar(horarioSelecionado);

        fecharTelaEditar();
    }

    //Atributos e métodos de EXIBIR HORÁRIO --------------------------------------------------------
    @FXML
    private Label descricaoHorario;
    @FXML
    private Label descricaoNomeMonitor;

    // AnchorPane esquerdo com dados dos visitantes
    @FXML
    protected void onJogosHorarioClicked(){
        Application.mudarCena("exibir-horario-jogos-view.fxml");
    }

    @FXML
    private TextField buscarVisitante;

    @FXML
    private Label statusHorario;

    @FXML
    protected void onBuscarVisitanteClicked(){
        // adicionar um método para buscar pelo nome
    }

    @FXML
    protected void onVoltarHorarioClicked(){
        Application.mudarCena("horarios-view.fxml");
    }

    @FXML
    private TableView<Visitante> visitantesHorario; // tenho que fazer um método para retornar na tabela os alunos que estao com a presença primeiro
    @FXML
    private TableColumn<Visitante, String> colNomeVisitante;
    @FXML
    private TableColumn<Visitante, Integer> colIdade;
    @FXML
    private TableColumn<Visitante, String> colGenero;
    @FXML
    private TableColumn<Visitante, String> colCurso;
    @FXML
    private TableColumn<Visitante, Void> colPresenca;

    @FXML
    private Label totalVisitantes;

    //AnchorPane direito com dados dos jogos
    @FXML
    protected void onVisitantesHorarioClicked(){
        Application.mudarCena("exibir-horario-visitantes-view.fxml");
    }

    @FXML
    private TextField buscarJogo;
    @FXML
    protected void onBuscarJogoClicked(){
        //implementar um método que busque o jogo pelo nome
    }

    @FXML
    private TableView<Jogo> jogosHorario;
    @FXML
    private TableColumn<Jogo, String> colTitulo;
    @FXML
    private TableColumn<Jogo, String> colTipo;
    @FXML
    private TableColumn<Jogo, String> colMarca;
    @FXML
    private TableColumn<Jogo, Integer> colTempoPartida;
    @FXML
    private TableColumn<Jogo, Void> colUtilizacao;
    @FXML
    private Label totalJogos;

    @FXML
    private void carregarTabelaVisitantes(){
        colNomeVisitante.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        List<Visitante> lista = DAOFactory.createHorarioDAO().buscarVisitantesHorario(horarioSelecionado.getId_horario());
        visitantesHorario.setItems(FXCollections.observableArrayList(lista));
        totalVisitantes.setText(String.valueOf(lista.size()));
    }

    @FXML
    private void carregarTabelaJogos(){
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colTempoPartida.setCellValueFactory(new PropertyValueFactory<>("tempo_partida"));
        List<Jogo> lista = DAOFactory.createHorarioDAO().buscarJogosHorario(horarioSelecionado.getId_horario());
        jogosHorario.setItems(FXCollections.observableArrayList(lista));
        totalJogos.setText(String.valueOf(lista.size()));
    }
}
