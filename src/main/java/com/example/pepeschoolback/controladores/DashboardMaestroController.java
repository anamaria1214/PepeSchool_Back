package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Grupo;
import com.example.pepeschoolback.modelo.documentos.Pregunta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardMaestroController implements Initializable {
    @FXML
    private TableColumn<Grupo, String> colMateria;

    @FXML
    private TableColumn<Grupo, String> colGrupo;

    @FXML
    private TableColumn<Grupo, String> colDia;

    @FXML
    private TableColumn<Grupo, String> colHora;

    @FXML private TableView<Grupo> tblGrupos;

    @FXML
    private StackPane contentArea;

    @FXML
    private VBox examenesView;

    @FXML
    private VBox gruposView;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private TextField txtBuscarExamen;

    private UsuarioActivo usuario = UsuarioActivo.getInstance();

    private final ListasDAO listasDAO;
    private ObservableList<Grupo> gruposList = FXCollections.observableArrayList();


    public DashboardMaestroController(ListasDAO listasDAO) {
        this.listasDAO = listasDAO;
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

    void mostrarGrupos() throws SQLException {
        List<Grupo> grupos= listasDAO.obtenerGrupoDoccente(usuario.getUserId());
        gruposList.setAll(grupos);
        tblGrupos.setItems(gruposList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMateria.setCellValueFactory(new PropertyValueFactory<>("materia"));
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        colDia.setCellValueFactory(new PropertyValueFactory<>("diaClase"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("horaClase"));
        try {
            mostrarGrupos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
