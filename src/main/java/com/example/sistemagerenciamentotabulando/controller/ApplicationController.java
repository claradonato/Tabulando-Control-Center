package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ApplicationController {
    @FXML
    private TextField loginUsuario;

    @FXML
    private TextField loginSenha;

    @FXML
    private Label usuarioOuSenhaIncorreto;

    @FXML
    protected void acessoPermitido() {Application.mudarCena("dashboard-view.fxml");}

    @FXML
    protected void onLoginClicked() {
        String usuario = loginUsuario.getText();
        String senha = loginSenha.getText();
        acessoPermitido();
        /*if(Objects.equals(usuario, "monitor") && Objects.equals(senha, "tabu123")){
            acessoPermitido();
        } else {
            usuarioOuSenhaIncorreto.setText("Usuário ou senha incorreta.");
        }*/
    }
}
