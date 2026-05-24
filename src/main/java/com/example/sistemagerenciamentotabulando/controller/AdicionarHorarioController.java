package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarHorarioController {
    @FXML
    private TextField diaSemana;

    @FXML
    private TextField turno;

    @FXML
    private TextField hora;

    @FXML
    private TextField nomeMonitor;

    @FXML
    private Button btnFechar;

    @FXML
    protected void fecharTela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onCancelarAdcHorarioClicked(){ fecharTela(); }

    @FXML
    protected void onAdcHorarioClicked(){
        Horario h = new Horario(diaSemana.getText(), turno.getText(), hora.getText(), nomeMonitor.getText());
        DAOFactory.createHorarioDAO().inserir(h);
        diaSemana.setText(" ");
        turno.setText(" ");
        hora.setText(" ");
        nomeMonitor.setText(" ");
    }
}
