package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarHorarioController{
    private Horario horario;

    @FXML
    private TextField diaSemana;

    @FXML
    private TextField turno;

    @FXML
    private TextField hora;

    @FXML
    private TextField nomeMonitor;

    @FXML
    public void carregarHorario(Horario h) {
        this.horario = h;
        diaSemana.setText(h.getDiaSemana());
        turno.setText(h.getTurno());
        hora.setText(h.getHora());
        nomeMonitor.setText(h.getNomeMonitor());
    }

    @FXML
    protected void onSalvarClicked(){
        horario.setDiaSemana(diaSemana.getText());
        horario.setTurno(turno.getText());
        horario.setHora(hora.getText());
        horario.setNomeMonitor(nomeMonitor.getText());
    }
}
