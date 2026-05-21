package com.example.sistemagerenciamentotabulando;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Scene scene;
    private static Stage stagePrincipal;

    @Override
    public void start(Stage stage) throws IOException {
        stagePrincipal = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("application-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema Tabulando");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public static Scene getScene(){
        return scene;
    }

    public static Scene criarTela(String url){
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(url));
        Stage stage = new Stage();
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Página Inicial");
        stage.setScene(scene);
        stage.show();

        return scene;
    }

    public static FXMLLoader trocarTela(String arquivoFXML, Stage stage){
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(arquivoFXML));
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return loader;
    }

    public static void main(String[] args) {
        launch();
    }
}
