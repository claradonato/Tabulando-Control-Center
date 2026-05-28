package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.model.dao.DAOFactory;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarCadastroController {
    private Visitante v;

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
        v.setNome(nome.getText());
        v.setMatricula(Integer.parseInt(matricula.getText()));
        v.setIdade(Integer.parseInt(idade.getText()));
        v.setGenero(genero.getText());
        v.setCurso(curso.getText());
        v.setTurno(turno.getText());
        v.setNivel_ensino(nivel_ensino.getText());
        v.setPossuiNEE(Boolean.valueOf(possuiNEE.getText()));

        DAOFactory.createVisitanteDAO().atualizar(v);
        fecharTela();
    }

    @FXML
    protected void carregarVisitante(Visitante v){
        this.v = v;

        nome.setText(v.getNome());
        matricula.setText(String.valueOf(v.getMatricula()));
        idade.setText(String.valueOf(v.getIdade()));
        genero.setText(v.getGenero());
        curso.setText(v.getCurso());
        turno.setText(v.getTurno());
        nivel_ensino.setText(v.getNivel_ensino());
        possuiNEE.setText(String.valueOf(v.getPossuiNEE()));
    }
}
