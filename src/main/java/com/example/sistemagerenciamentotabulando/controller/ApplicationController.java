package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    @FXML
    private TextField loginUsuario;
    @FXML
    private PasswordField loginSenha;
    @FXML
    private Label usuarioOuSenhaIncorreto;
    private final Map<String, String> usuarios = new HashMap<>();

    @FXML
    protected void acessoPermitido() {Application.mudarCena("dashboard-view.fxml");}

    @FXML
    protected void onLoginClicked() {
        String usuario = loginUsuario.getText();
        String senha = loginSenha.getText().trim();

        if(usuario.isEmpty() || senha.isEmpty()){
            usuarioOuSenhaIncorreto.setText("Preencha usuário e senha.");
            return;
        }

        if(usuarios.containsKey(usuario) && usuarios.get(usuario).equals(senha)){
            usuarioOuSenhaIncorreto.setText("");
            acessoPermitido();
        }
        else{
            usuarioOuSenhaIncorreto.setText("Usuário ou senha incorretos.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarios.put("claradonato", "123");
        usuarios.put("thiagoh", "456");
        usuarios.put("thiagor", "789");
        usuarios.put("vagner", "1011");
    }
}
