module com.example.sistemagerenciamentotabulando {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sistemagerenciamentotabulando to javafx.fxml;
    exports com.example.sistemagerenciamentotabulando;
    exports com.example.sistemagerenciamentotabulando.controller;
    opens com.example.sistemagerenciamentotabulando.controller to javafx.fxml;

    opens com.example.sistemagerenciamentotabulando.model.entities to javafx.base;
}