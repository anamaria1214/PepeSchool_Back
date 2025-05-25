package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.DocenteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

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

    private Connection conexion;

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

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
    void mostrarCrearPregunta(ActionEvent event) throws IOException {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        ListasDAO listasDAO = new ListasDAO(oracleConnector);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/PreguntasView.fxml"));
        loader.setController(new PreguntasViewController(listasDAO));
        Parent root = loader.load();

        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActual.close();

        Stage stage= new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Pepe School");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void mostrarExamenes(ActionEvent event) throws IOException {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        ListasDAO listasDAO = new ListasDAO(oracleConnector);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/ExamenView.fxml"));
        loader.setController(new ExamenViewController(listasDAO));
        Parent root = loader.load();

        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActual.close();

        Stage stage= new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Pepe School");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void mostrarGrupos(ActionEvent event) {

    }

}
