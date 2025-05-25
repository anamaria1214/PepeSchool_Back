package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.DocenteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Pregunta;
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
import java.util.List;
import java.util.ResourceBundle;

public class PreguntasViewController implements Initializable {

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnFormularioPregunta;
    @FXML
    private TableView<Pregunta> preguntasTable;
    @FXML private TableColumn<Pregunta, Integer> idColumn;
    @FXML private TableColumn<Pregunta, String> enunciadoColumn;
    @FXML private TableColumn<Pregunta, String> tipoColumn;
    @FXML private TableColumn<Pregunta, String> respuestaColumn;
    @FXML private TableColumn<Pregunta, String> pesoColumn;

    private ObservableList<Pregunta> preguntasList = FXCollections.observableArrayList();
    private UsuarioActivo usuario = UsuarioActivo.getInstance();
    private final ListasDAO listasDAO;

    public PreguntasViewController(ListasDAO listasDAO) {
        this.listasDAO = listasDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        enunciadoColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        respuestaColumn.setCellValueFactory(new PropertyValueFactory<>("respuesta"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));

        try {
            cargarPreguntas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarPreguntas() throws SQLException {
        List<Pregunta> preguntasDocente= listasDAO.obtenerPreguntasDocente(usuario.getUserId());
        preguntasList.setAll(preguntasDocente);
        preguntasTable.setItems(preguntasList);
    }

    @FXML
    private void mostrarFormularioPregunta() throws SQLException, IOException {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        ListasDAO listasDAO = new ListasDAO(oracleConnector);

        DocenteDAO docenteDAO= new DocenteDAO(oracleConnector);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/FormularioPregunta.fxml"));
        loader.setController(new FormularioPregunta(listasDAO, docenteDAO));
        Parent root = loader.load();

        Stage stage= new Stage();
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
