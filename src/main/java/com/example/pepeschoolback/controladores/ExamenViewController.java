package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.DocenteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Examen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ExamenViewController implements Initializable {

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnFormularioExamen;
    @FXML
    private TableView<Examen> ExamenTable;
    @FXML private TableColumn<Examen, String> nombreColumn;
    @FXML private TableColumn<Examen, String> descripcionColum;
    @FXML private TableColumn<Examen, Integer> cantPreguntasColumn;
    @FXML private TableColumn<Examen, Date> fechaPresenColumn;
    @FXML private TableColumn<Examen, String> materiaColumn;

    private ObservableList<Examen> examenList = FXCollections.observableArrayList();
    private UsuarioActivo usuario = UsuarioActivo.getInstance();
    private final ListasDAO listasDAO;

    public ExamenViewController(ListasDAO listasDAO) {
        this.listasDAO = listasDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionColum.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        cantPreguntasColumn.setCellValueFactory(new PropertyValueFactory<>("cantpreguntas"));
        fechaPresenColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPresentacion"));
        materiaColumn.setCellValueFactory(new PropertyValueFactory<>("materia"));

        try {
            cargarExamenes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarExamenes() throws SQLException {
        List<Examen> examenesDocente= listasDAO.obtenerExamenDocente(usuario.getUserId());
        examenList.setAll(examenesDocente);
        ExamenTable.setItems(examenList);
    }

    @FXML
    private void mostrarFormularioExamen() throws SQLException, IOException {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        ListasDAO listasDAO = new ListasDAO(oracleConnector);

        DocenteDAO docenteDAO= new DocenteDAO(oracleConnector);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/FormularioExamen.fxml"));
        loader.setController(new FormularioExamenController(listasDAO, docenteDAO));
        Parent root = loader.load();

        Stage stage= new Stage();

        stage.setWidth(1000);  // Ancho inicial
        stage.setHeight(750);  // Alto inicial
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        Scene scene = new Scene(root);
        stage.setTitle("Pepe School");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void cancelar() throws IOException {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/DashboardMaestro.fxml"));
        loader.setController(new DashboardMaestroController());
        Parent root = loader.load();

        Stage stage= new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Pepe School");
        stage.setScene(scene);
        stage.show();
    }

}
