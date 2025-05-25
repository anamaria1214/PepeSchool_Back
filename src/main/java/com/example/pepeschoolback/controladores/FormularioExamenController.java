package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.DocenteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Pregunta;
import com.example.pepeschoolback.modelo.documentos.PreguntaExamen;
import com.example.pepeschoolback.modelo.vo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FormularioExamenController implements Initializable {
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private TextField txtNotaMinima;
    @FXML private TextField txtLimiteTiempo;
    @FXML private TextField txtCantPreguntasExamen;
    @FXML private TextField txtCantPreguntasEstudiante;

    @FXML private DatePicker dpFechaPresentacion;
    @FXML private ComboBox<Materia> cbMateria;
    @FXML private ComboBox<Categoria> cbCategoria;
    @FXML private ComboBox<Tema> cbTema;
    @FXML private ComboBox<TipoPregunta> cbTipoPregunta;
    @FXML private TextField txtPeso;
    @FXML private TableView<Pregunta> tblPreguntasDisponibles;
    @FXML private TableColumn<Pregunta, Integer> colIdPregunta;
    @FXML private TableColumn<Pregunta, String> colEnunciado;
    @FXML private TableColumn<Pregunta, String> colRespuesta;
    @FXML private TableColumn<Pregunta, String> colTipo;

    @FXML private TableView<PreguntaExamen> tblPreguntasSeleccionadas;
    @FXML private TableColumn<PreguntaExamen, Integer> colIdPreguntaSel;
    @FXML private TableColumn<PreguntaExamen, String> colEnunciadoSel;
    @FXML private TableColumn<PreguntaExamen, String> colRespuestaSel;
    @FXML private TableColumn<PreguntaExamen, Integer> colPeso;
    @FXML private TableColumn<PreguntaExamen, Void> colEliminar;

    private ObservableList<Pregunta> preguntasDisponibles = FXCollections.observableArrayList();
    private ObservableList<PreguntaExamen> preguntasSeleccionadas = FXCollections.observableArrayList();

    private final ListasDAO listasDAO;
    private final DocenteDAO docenteDAO;

    private UsuarioActivo usuario = UsuarioActivo.getInstance();

    public FormularioExamenController(ListasDAO listasDAO, DocenteDAO docenteDAO) {
        this.listasDAO = listasDAO;
        this.docenteDAO = docenteDAO;
    }

    private void cargarComboboxes() throws SQLException {
        List<Categoria> categorias = listasDAO.obtenerTodosCategoria();
        cbCategoria.getItems().addAll(categorias);

        List<Tema> temas= listasDAO.obtenerTodosTema();
        cbTema.getItems().addAll(temas);

        List<TipoPregunta> tipos= listasDAO.obtenerTodosTipoPregunta();
        cbTipoPregunta.getItems().addAll(tipos);

        List<Materia> materias= listasDAO.obtenerMateriasDocente(usuario.getUserId());
        cbMateria.getItems().addAll(materias);
    }


    @FXML
    private void eliminarPregunta(PreguntaExamen pregunta) {
        preguntasSeleccionadas.remove(pregunta);
    }

    @FXML
    private void guardarExamen() throws Exception {
        try{
            int idExamen= docenteDAO.crearExamen(0, txtNombre.getText(), txtDescripcion.getText(),
                    Integer.parseInt(txtCantPreguntasExamen.getText()), Integer.parseInt(txtNotaMinima.getText()),
                    Integer.parseInt(txtLimiteTiempo.getText()),
                    dpFechaPresentacion.getValue(),cbMateria.getValue().getId(), cbCategoria.getValue().getId(),
                    cbTema.getValue().getId(),cbTema.getValue().getId(),Integer.parseInt(txtCantPreguntasEstudiante.getText()));
            JOptionPane.showMessageDialog(null, "El examen se agregó correctamente");
            System.out.println(idExamen);
            int cantPregSelec= preguntasSeleccionadas.size();
            System.out.println(cantPregSelec);
            for(PreguntaExamen pregunta:preguntasSeleccionadas){
                System.out.println(pregunta.getId()+", "+pregunta.getEnunciado());
                try {
                    docenteDAO.agregarPreguntasExamen(idExamen, pregunta.getId(), pregunta.getPeso());
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error con pregunta ID " + pregunta.getId(), e.getMessage());
                    return;
                }
            }
            mostrarAlerta("Éxito", "Todas las preguntas fueron asignadas correctamente");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void cancelar() throws IOException {
        limpiarFormulario();
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        ListasDAO listasDAO = new ListasDAO(oracleConnector);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/ExamenView.fxml"));
        loader.setController(new ExamenViewController(listasDAO));
        Parent root = loader.load();

        Stage stage= new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Pepe School");
        stage.setScene(scene);
        stage.show();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtDescripcion.clear();
        txtNotaMinima.clear();
        txtLimiteTiempo.clear();
        dpFechaPresentacion.setValue(LocalDate.now());
        preguntasDisponibles.clear();
        preguntasSeleccionadas.clear();
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void cargarTabla() throws SQLException {
        colIdPregunta.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEnunciado.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colRespuesta.setCellValueFactory(new PropertyValueFactory<>("respuesta"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        List<Pregunta> preguntas= listasDAO.obtenerPreguntastemaytipo(cbTema.getValue().getId(), cbTipoPregunta.getValue().getId());
        preguntasDisponibles.setAll(preguntas);
        tblPreguntasDisponibles.setItems(preguntasDisponibles);
    }


    private void configurarColumnasSeleccionadas() {
        colIdPreguntaSel.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEnunciadoSel.setCellValueFactory(new PropertyValueFactory<>("enunciado"));
        colRespuestaSel.setCellValueFactory(new PropertyValueFactory<>("respuesta"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        // Botón para eliminar preguntas seleccionadas
        colEliminar.setCellFactory(param -> new TableCell<PreguntaExamen, Void>() {
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEliminar.setOnAction(event -> {
                    PreguntaExamen pregunta = getTableView().getItems().get(getIndex());
                    preguntasSeleccionadas.remove(pregunta);
                });
            }
        });
    }

    @FXML
    private void agregarPregunta(ActionEvent event) {
        Pregunta preguntaSeleccionada = tblPreguntasDisponibles.getSelectionModel().getSelectedItem();

        if (preguntaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor seleccione una pregunta de la lista");
            return;
        }

        String pesoTexto = txtPeso.getText().trim();
        if (pesoTexto.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese un peso para la pregunta");
            return;
        }

        try {
            int peso = Integer.parseInt(pesoTexto);

            if (peso <= 0) {
                mostrarAlerta("Error", "El peso debe ser un número positivo");
                return;
            }

            PreguntaExamen preguntaExamen = new PreguntaExamen(
                    preguntaSeleccionada.getId(),
                    preguntaSeleccionada.getDescripcion(),  // Asegúrate que esto coincida con tu clase Pregunta
                    preguntaSeleccionada.getRespuesta(),
                    peso
            );

            if (preguntasSeleccionadas.stream().anyMatch(p -> p.getId() == preguntaExamen.getId())) {
                mostrarAlerta("Advertencia", "Esta pregunta ya fue agregada al examen");
                return;
            }

            preguntasSeleccionadas.add(preguntaExamen);
            tblPreguntasDisponibles.getSelectionModel().clearSelection();
            txtPeso.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El peso debe ser un número válido");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarComboboxes();
            configurarColumnasSeleccionadas();
            tblPreguntasSeleccionadas.setItems(preguntasSeleccionadas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
