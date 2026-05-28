package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarVisitanteController {
    @FXML
    private TextField nome;
    @FXML
    private TextField matricula;
    @FXML
    private TextField idade;
    @FXML
    private TextField genero;
    @FXML
    private TextField nivel_ensino;
    @FXML
    private TextField curso;
    @FXML
    private TextField turno;
    @FXML
    private TextField possuiNEE;

    @FXML
    private Button btnFechar;

    @FXML
    protected void fecharTela() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSalvarVisitanteClicked(){
        Visitante v = new Visitante((int) Long.parseLong(matricula.getText()), nome.getText(), Integer.parseInt(idade.getText()), genero.getText(), nivel_ensino.getText(), curso.getText(), turno.getText(), Boolean.valueOf(possuiNEE.getText()));
        DAOFactory.createVisitanteDAO().inserir(v);
        matricula.setText(" ");
        nome.setText(" ");
        idade.setText(" ");
        genero.setText(" ");
        nivel_ensino.setText(" ");
        curso.setText(" ");
        turno.setText(" ");
        possuiNEE.setText(" ");
        fecharTela();
    }
}
