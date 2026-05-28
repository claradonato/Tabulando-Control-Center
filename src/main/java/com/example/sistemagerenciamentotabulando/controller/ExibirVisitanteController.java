package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class ExibirVisitanteController {
    private Visitante v;

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
        this.v = v;

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
        FXMLLoader loader = Application.abrirNovaJanela("editar-cadastro-view.fxml");
        EditarCadastroController controller = loader.getController();
        controller.carregarVisitante(v);
    }

    @FXML
    protected void onRefreshButtonClicked(){
        carregarVisitante(v);
    }
}
