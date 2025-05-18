package com.example.pepeschoolback.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardEstudianteController {
    @FXML private Label lblNombreUsuario;
    @FXML private StackPane contentArea;
    @FXML
    private VBox examenesPendientesView;
    @FXML private VBox examenesMateriaView;
    @FXML private VBox examenesGrupoView;
    @FXML private VBox historialView;

    // Tablas y comboboxes
    @FXML private TableView<?> tblExamenesPendientes;
    @FXML private ComboBox<String> cbMaterias;
    @FXML private TableView<?> tblExamenesMateria;
    @FXML private ComboBox<String> cbGrupos;
    @FXML private TableView<?> tblExamenesGrupo;
    @FXML private TableView<?> tblHistorial;

    private ObservableList<?> examenesPendientes = FXCollections.observableArrayList();
    private ObservableList<?> examenesPorMateria = FXCollections.observableArrayList();
    private ObservableList<?> examenesPorGrupo = FXCollections.observableArrayList();
    private ObservableList<?> historialExamenes = FXCollections.observableArrayList();

}
