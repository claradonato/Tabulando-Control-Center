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
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HorariosController implements Initializable {
    private Horario horarioSelecionado;

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
                    horarioSelecionado = getTableView().getItems().get(getIndex());
                    Application.mudarCena("exibir-horario-view.fxml");
                    preencherCamposEdicao(horarioSelecionado);
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
        if(listagemHorarios != null){
            configurarColunas();
            configurarColunaHora();
            configurarColunaAcoes();
            carregarDados();
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

    //Atributos e métodos de ADICIONAR HORÁRIO ---------------------------------------------
    @FXML
    private TextField diaSemana;
    @FXML
    private TextField turno;
    @FXML
    private TextField hora;
    @FXML
    private TextField nomeMonitor;
    @FXML
    private Button btnFecharAdicionar;
    @FXML
    protected void fecharTelaAdicionar() {
        Stage stage = (Stage) btnFecharAdicionar.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void onAdcHorarioClicked(){
        Horario h = new Horario(diaSemana.getText(), turno.getText(), hora.getText(), nomeMonitor.getText());
        DAOFactory.createHorarioDAO().inserir(h);
        diaSemana.setText(" ");
        turno.setText(" ");
        hora.setText(" ");
        nomeMonitor.setText(" ");
        carregarDados();
    }

    //Atributos e métodos de EDITAR HORARIO --------------------------------------------------------
    @FXML
    private Button btnFecharEditar;

    @FXML
    protected void fecharTelaEditar() {
        Stage stage = (Stage) btnFecharEditar.getScene().getWindow();
        stage.close();
    }
    // fazer "horarioSelecionado = h;" antes de abrir a tela

    @FXML
    protected void onSalvarClicked(){
        horarioSelecionado.setDiaSemana(diaSemana.getText());
        horarioSelecionado.setTurno(turno.getText());
        horarioSelecionado.setHora(hora.getText());
        horarioSelecionado.setNomeMonitor(nomeMonitor.getText());
        DAOFactory.createHorarioDAO().atualizar(horarioSelecionado);

        fecharTelaEditar();
    }

    //Atributos e métodos de EXIBIR HORÁRIO --------------------------------------------------------
    @FXML
    private Label descricaoHorario;
    @FXML
    private Label descricaoNomeMonitor;

    private void preencherCamposEdicao(Horario h){
        diaSemana.setText(h.getDiaSemana());
        turno.setText(h.getTurno());
        hora.setText(h.getHora());
        nomeMonitor.setText(h.getNomeMonitor());
    }

    @FXML
    protected void onAtualizarHorarioClicked(){
        if(horarioSelecionado == null){
            return;
        }
        Application.abrirNovaJanela("editar-horario-view.fxml");
        preencherCamposEdicao(horarioSelecionado);
    }

    // AnchorPane esquerdo com dados dos visitantes
    @FXML
    private TextField buscarVisitante;

    @FXML
    protected void onBuscarVisitanteClicked(){

    }

    @FXML
    protected void onVoltarHorarioClicked(){
        Application.mudarCena("horarios-view.fxml");
    }

    @FXML
    protected void onAdicionarVisitanteClicked(){
        Application.abrirNovaJanela("adicionar-visitante-view.fxml");
        buscarVisitante.clear();
        carregarTabelaVisitantes();
    }

    @FXML
    private TableView<Visitante> visitantesHorario;
    @FXML
    private TableColumn<Visitante, String> colNomeVisitante;
    @FXML
    private TableColumn<Visitante, Integer> colIdade;
    @FXML
    private TableColumn<Visitante, String> colGenero;
    @FXML
    private TableColumn<Visitante, String> colCurso;

    @FXML
    private Label totalVisitantes;

    //AnchorPane direito com dados dos jogos

    @FXML
    private TextField buscarJogo;
    @FXML
    protected void onBuscarJogoClicked(){
        //implementar
    }
    @FXML
    protected void onAdicionarJogoClicked(){
        Integer idJogo = Integer.parseInt(buscarJogo.getText());
        DAOFactory.createHorarioDAO().adicionarJogoHorario(idJogo, horarioSelecionado.getId_horario());
        buscarJogo.clear();
        carregarTabelaJogos();
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
