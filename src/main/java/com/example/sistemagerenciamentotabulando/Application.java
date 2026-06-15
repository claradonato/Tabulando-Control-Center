package com.example.sistemagerenciamentotabulando;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage stage; //transformar isso apenas em um stage única

    @Override
    public void start(Stage stage) throws IOException {
        Application.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("application-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema Tabulando");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static FXMLLoader mudarCena(String url){
        try{
            stage.getScene().setRoot(new FXMLLoader(Application.class.getResource(url)).load());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public static FXMLLoader abrirNovaJanela(String arquivoFXML) {
        FXMLLoader loader = new FXMLLoader(
                Application.class.getResource(arquivoFXML)
        );
        try {
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }

    public static void main(String[] args) {
        launch();
    }
}
