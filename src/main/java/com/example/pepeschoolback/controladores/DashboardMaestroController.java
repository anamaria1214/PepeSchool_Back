package com.example.pepeschoolback.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardMaestroController {
    @FXML
    private TableColumn<?, ?> colAccionesExamen;

    @FXML
    private TableColumn<?, ?> colAccionesGrupo;

    @FXML
    private TableColumn<?, ?> colEstudiantesGrupo;

    @FXML
    private TableColumn<?, ?> colFechaExamen;

    @FXML
    private TableColumn<?, ?> colIdExamen;

    @FXML
    private TableColumn<?, ?> colIdGrupo;

    @FXML
    private TableColumn<?, ?> colMateriaExamen;

    @FXML
    private TableColumn<?, ?> colMateriaGrupo;

    @FXML
    private TableColumn<?, ?> colNombreExamen;

    @FXML
    private TableColumn<?, ?> colNombreGrupo;

    @FXML
    private TableColumn<?, ?> colPreguntasExamen;

    @FXML
    private StackPane contentArea;

    @FXML
    private VBox examenesView;

    @FXML
    private VBox gruposView;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private TableView<?> tblExamenes;

    @FXML
    private TableView<?> tblGrupos;

    @FXML
    private TextField txtBuscarExamen;

    @FXML
    void buscarExamenes(ActionEvent event) {

    }

    @FXML
    void cerrarSesion(ActionEvent event) {

    }

    @FXML
    void mostrarCrearExamen(ActionEvent event) {

    }

    @FXML
    void mostrarCrearPregunta(ActionEvent event) {

    }

    @FXML
    void mostrarExamenes(ActionEvent event) {

    }

    @FXML
    void mostrarGrupos(ActionEvent event) {

    }

}
