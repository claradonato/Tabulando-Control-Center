package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
        //implementar
    }

    @FXML
    protected void onAdicionarVisitanteClicked(){
        //implementar
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
        //implementar
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
    public void carregarHorario(Horario h) {
        this.horario = h;

        descricaoHorario.setText(h.getDiaSemana() + " - " + h.getTurno() + "(" + h.getHora() + ")");
        nomeMonitor.setText("Monitor: " + h.getNomeMonitor());
    }

    @FXML
    protected void onVoltarHorarioClicked(){
        Application.trocarTela("horarios-view.fxml", Application.getStagePrincipal());
    }
}
