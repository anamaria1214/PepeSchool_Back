package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.modelo.documentos.Pregunta;
import com.example.pepeschoolback.modelo.documentos.PreguntaExamen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FormularioExamenController {
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private TextField txtNotaMinima;
    @FXML private TextField txtLimiteTiempo;
    @FXML
    private DatePicker dpFechaPresentacion;
    @FXML private ComboBox<String> cbMateria;
    @FXML private ComboBox<String> cbCategoria;
    @FXML private ComboBox<String> cbTema;

    // Sección de preguntas
    @FXML private ComboBox<String> cbTipoPregunta;
    @FXML private TextField txtPeso;
    @FXML private TableView<Pregunta> tblPreguntasDisponibles;
    @FXML private TableView<PreguntaExamen> tblPreguntasSeleccionadas;

    // Listas de datos
    private ObservableList<Pregunta> preguntasDisponibles = FXCollections.observableArrayList();
    private ObservableList<PreguntaExamen> preguntasSeleccionadas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar tablas
        configurarTablaPreguntasDisponibles();
        configurarTablaPreguntasSeleccionadas();

        // Cargar comboboxes (simulado)
        cargarComboboxes();

        // Establecer valores por defecto
        dpFechaPresentacion.setValue(LocalDate.now());
        txtPeso.setText("1");
    }

    private void configurarTablaPreguntasDisponibles() {
        // Configuración de columnas y botones de acción
        // ...
    }

    private void configurarTablaPreguntasSeleccionadas() {
        // Configuración de columnas y botones para eliminar
        // ...
    }

    private void cargarComboboxes() {
        // Simulación de carga de datos
        cbMateria.getItems().addAll("Matemáticas", "Ciencias", "Historia");
        cbCategoria.getItems().addAll("Examen Parcial", "Examen Final", "Quiz");
        cbTema.getItems().addAll("Álgebra", "Geometría", "Trigonometría");
    }

    @FXML
    private void buscarPreguntas() {
        String tipoPregunta = cbTipoPregunta.getValue();
        String tema = cbTema.getValue();

        if (tipoPregunta == null || tema == null) {
            mostrarAlerta("Debe seleccionar un tipo de pregunta y un tema");
            return;
        }

        // Simulación de búsqueda de preguntas
        preguntasDisponibles.clear();
        preguntasDisponibles.addAll(
                new Pregunta(1, "¿Cuál es la capital de Francia?", "Selección Múltiple"),
                new Pregunta(2, "¿2 + 2 = 4?", "Verdadero/Falso"),
                new Pregunta(3, "¿Quién escribió Don Quijote?", "Respuesta Corta")
        );
    }

    @FXML
    private void agregarPregunta(Pregunta pregunta) {
        try {
            int peso = Integer.parseInt(txtPeso.getText());
            if (peso <= 0) {
                mostrarAlerta("El peso debe ser un número positivo");
                return;
            }

            PreguntaExamen preguntaSel = new PreguntaExamen(
                    pregunta.getId(),
                    pregunta.getEnunciado(),
                    pregunta.getTipo(),
                    peso
            );

            preguntasSeleccionadas.add(preguntaSel);
        } catch (NumberFormatException e) {
            mostrarAlerta("El peso debe ser un número válido");
        }
    }

    @FXML
    private void eliminarPregunta(PreguntaExamen pregunta) {
        preguntasSeleccionadas.remove(pregunta);
    }

    @FXML
    private void guardarExamen() {
        // Validar campos
        if (txtNombre.getText().isEmpty() || preguntasSeleccionadas.isEmpty()) {
            mostrarAlerta("Debe completar el nombre del examen y agregar al menos una pregunta");
            return;
        }

        try {
            //Aquí debo implementar la lógica para guardar el examen
            mostrarAlerta("Examen guardado exitosamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
        } catch (NumberFormatException e) {
            mostrarAlerta("Los campos numéricos deben contener valores válidos");
        } catch (Exception e) {
            mostrarAlerta("Error al guardar el examen: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        limpiarFormulario();
        // Cerrar ventana o volver atrás
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

    private void mostrarAlerta(String mensaje) {
        mostrarAlerta(mensaje, Alert.AlertType.ERROR);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tipo == Alert.AlertType.ERROR ? "Error" : "Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
