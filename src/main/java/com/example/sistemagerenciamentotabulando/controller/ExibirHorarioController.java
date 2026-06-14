package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ExibirHorarioController {
    private Horario horario;

    @FXML
    private Label descricaoHorario;
    @FXML
    private Label nomeMonitor;

    @FXML
    protected void onAtualizarHorarioClicked(){
        FXMLLoader loader = Application.abrirNovaJanela("editar-horario-view.fxml");
        EditarHorarioController controller = loader.getController();
        controller.carregarHorario(horario);
    }

    // AnchorPane esquerdo com dados dos visitantes
    @FXML
    private TextField buscarVisitante;

    @FXML
    protected void onBuscarVisitanteClicked(){

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
        DAOFactory.createHorarioDAO().adicionarJogoHorario(idJogo, horario.getId_horario());
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
        List<Visitante> lista = DAOFactory.createHorarioDAO().buscarVisitantesHorario(horario.getId_horario());
        visitantesHorario.setItems(FXCollections.observableArrayList(lista));
        totalVisitantes.setText(String.valueOf(lista.size()));
    }

    @FXML
    private void carregarTabelaJogos(){
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colTempoPartida.setCellValueFactory(new PropertyValueFactory<>("tempo_partida"));
        List<Jogo> lista = DAOFactory.createHorarioDAO().buscarJogosHorario(horario.getId_horario());
        jogosHorario.setItems(FXCollections.observableArrayList(lista));
        totalJogos.setText(String.valueOf(lista.size()));
    }

    @FXML
    public void carregarHorario(Horario h) {
        this.horario = h;

        descricaoHorario.setText(h.getDiaSemana() + " - " + h.getTurno() + "(" + h.getHora() + ")");
        nomeMonitor.setText("Monitor: " + h.getNomeMonitor());
        carregarTabelaVisitantes();
        carregarTabelaJogos();
    }

    @FXML
    protected void onVoltarHorarioClicked(){
        Application.mudarCena("horarios-view.fxml");
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarHorario(this.horario);
    }
}
