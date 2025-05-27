package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Examen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class DashboardEstudianteController {
    @FXML private Label lblNombreUsuario;
    @FXML private StackPane contentArea;
    @FXML
    private VBox examenesPendientesView;
    @FXML private VBox examenesMateriaView;
    @FXML private VBox examenesGrupoView;
    @FXML private VBox historialView;

    // Tablas y comboboxes
    @FXML private TableView<Examen> tblExamenesPendientes;
    @FXML private TableColumn<Examen, String> colMateriaPendiente;
    @FXML private TableColumn<Examen, String> colNombrePendiente;
    @FXML private TableColumn<Examen, Integer> colFechaLimite;
    @FXML private TableColumn<Examen, String> colPresentar;
    @FXML private ComboBox<String> cbMaterias;
    @FXML private TableView<Examen> tblExamenesMateria;
    @FXML private ComboBox<String> cbGrupos;
    @FXML private TableView<Examen> tblExamenesGrupo;
    @FXML private TableView<Examen> tblHistorial;

    private ObservableList<?> examenesPendientes = FXCollections.observableArrayList();
    private ObservableList<?> examenesPorMateria = FXCollections.observableArrayList();
    private ObservableList<?> examenesPorGrupo = FXCollections.observableArrayList();
    private ObservableList<?> historialExamenes = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Configurar columnas
        colNombrePendiente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFechaLimite.setCellValueFactory(new PropertyValueFactory<>("fechaPresentacion"));
        colPresentar.setText("Presentar");
        colMateriaPendiente.setCellValueFactory(new PropertyValueFactory<>("materia"));
        try {
            OracleConnector connector = new OracleConnector();
            connector.connect();
            ListasDAO listasDAO = new ListasDAO(connector);
            List<Examen> examenes = listasDAO.obtenerExamenesEstudiantePendiente();

            ObservableList<Examen> examenesObservable = FXCollections.observableArrayList(examenes);
            tblExamenesPendientes.setItems(examenesObservable);
            tblExamenesPendientes.refresh();

            // Almacenar el nombre del examen seleccionado y redirigir a nueva ventana
            tblExamenesPendientes.setOnMouseClicked(event -> {
                Examen examenSeleccionado = tblExamenesPendientes.getSelectionModel().getSelectedItem();
                if (examenSeleccionado != null) {
                    String nombreExamen = examenSeleccionado.getNombre();
                    try {
                        int id = listasDAO.obtenerIdPorNombreExamen(nombreExamen);
                        System.out.println("el id del examen es: " + id);
                        UsuarioActivo.getInstance().setIdExamen(id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    // Aquí puedes almacenar el nombre en una variable estática, singleton, o pasarlo a la nueva ventana
                    // Ejemplo: ExamenSeleccionadoHolder.setNombre(nombreExamen);

                    // Redirigir a nueva ventana
                    try {
                        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/responder_examen.fxml"));
                        javafx.scene.Parent root = loader.load();
                        javafx.stage.Stage stage = new javafx.stage.Stage();
                        stage.setScene(new javafx.scene.Scene(root));
                        stage.show();

                        // Opcional: cerrar la ventana actual
                        tblExamenesPendientes.getScene().getWindow().hide();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
