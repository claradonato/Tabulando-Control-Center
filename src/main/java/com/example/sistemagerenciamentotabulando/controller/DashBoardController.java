package com.example.sistemagerenciamentotabulando.controller;

import com.example.sistemagerenciamentotabulando.Application;
import javafx.fxml.FXML;

public class DashBoardController {
    @FXML
    protected void onHorariosClicked(){
        Application.mudarCena("horarios-view.fxml");
    }

    @FXML
    protected void onJogosClicked(){ Application.mudarCena("jogos-view.fxml"); }

    @FXML
    protected void onVisitantesClicked(){ Application.mudarCena("visitantes-view.fxml");}
}
