package com.example.pepeschoolback.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ResponderExamenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnFinalizar;

    @FXML
    private GridPane gridPreguntas;

    @FXML
    void finalizarExamen(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnFinalizar != null : "fx:id=\"btnFinalizar\" was not injected: check your FXML file 'responderExamen.fxml'.";
        assert gridPreguntas != null : "fx:id=\"gridPreguntas\" was not injected: check your FXML file 'responderExamen.fxml'.";

    }

}
