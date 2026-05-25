package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import javafx.fxml.FXML;

public class DashBoardController {
    @FXML
    protected void onHorariosClicked(){
        Application.criarTela("horarios-view.fxml");
    }

    @FXML
    protected void onJogosClicked(){ Application.criarTela("jogos-view.fxml"); }
}
