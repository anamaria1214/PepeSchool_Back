package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.DocenteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.modelo.vo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormularioPregunta implements Initializable{
    @FXML
    private Button cancelarBtn;

    @FXML
    private CheckBox esCorrectaCheck;

    @FXML
    private Button guardarBtn;

    @FXML
    private TextField nuevaOpcionField;

    @FXML
    private VBox opcionesContainer;

    @FXML
    private Label opcionesHelpText;

    @FXML
    private VBox opcionesList;

    @FXML
    private TextArea pregunta;

    @FXML
    private ComboBox<Estado> estadoCombo;

    @FXML
    private ComboBox<Dificultad> dificultadCombo;

    @FXML
    private ComboBox<String> preguntaPadreCombo;

    @FXML
    private ComboBox<Tema> temaCombo;

    @FXML
    private ComboBox<TipoPregunta> tipoPreguntaCombo;

    @FXML
    private ComboBox<Visibilidad> visibilidadCombo;

    // Controles para ordenar elementos
    @FXML private VBox ordenarContainer;
    @FXML private ListView<String> ordenarList;
    @FXML private TextField nuevoElementoOrdenarField;

    @FXML private VBox emparejarContainer;
    @FXML private VBox verdaderoFalsoContainer;
    @FXML private TableView<ObservableList<String>> emparejarTable;
    @FXML private TextField nuevoConceptoAField;
    @FXML private TextField nuevoConceptoBField;

    @FXML private VBox completarContainer;
    @FXML private TableView<ObservableList<String>> respuestasCompletarTable;
    @FXML private TextField numeroEspacioField;
    @FXML private TextField respuestasCompletarField;

    @FXML private VBox opcionesMultiplesContainer;
    @FXML private Label ayudaLabel;

    private ObservableList<String> opciones = FXCollections.observableArrayList();
    private ObservableList<Boolean> opcionesCorrectas = FXCollections.observableArrayList();
    private ObservableList<String> parejas = FXCollections.observableArrayList();

    private final ListasDAO listasDAO;
    private final DocenteDAO docenteDAO;

    public FormularioPregunta(ListasDAO listasDAO, DocenteDAO docenteDAO) {
        this.listasDAO = listasDAO;
        this.docenteDAO = docenteDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarDatosIniciales();
            System.out.println(obtenerRespuestaCorrecta());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void agregarPregunta() {
        String tipo = tipoPreguntaCombo.getValue().getNombre();
        String respuesta= "";
        switch(tipo) {
            case "Seleccion multiple unica respuesta":
                respuesta=obtenerRespuestaCorrecta();
                int idPregunta=0;
                idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                        0,dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                        tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId());
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
                for(int i=0;i< obtenerOpcionesConEstado().size();i++){
                    docenteDAO.agregarOpciones(obtenerOpcionesConEstado().get(i).getTexto(), obtenerOpcionesConEstado().get(i).getEsCorrecta(),idPregunta+1);
                }

                break;

            case "ordenar":
                break;

            case "emparejar":
                 break;

            case "completar":
              break;

            case "falso y verdadero":

            default:
                break;
        }
    }


    private void cargarDatosIniciales() throws SQLException {
        List<Dificultad> dificultad = listasDAO.obtenerTodosDificultad();
        dificultadCombo.getItems().addAll(dificultad);

        List<Visibilidad> visibilidads= listasDAO.obtenerTodosVisibilidad();
        visibilidadCombo.getItems().addAll(visibilidads);

        List<Estado> estados= listasDAO.obtenerTodosEstado();
        estadoCombo.getItems().addAll(estados);

        List<Tema> temas= listasDAO.obtenerTodosTema();
        temaCombo.getItems().addAll(temas);

        List<TipoPregunta> tipoPreguntas= listasDAO.obtenerTodosTipoPregunta();
        tipoPreguntaCombo.getItems().addAll(tipoPreguntas);

    }

    @FXML
    private void cambiarTipoPregunta() {
        String tipo = tipoPreguntaCombo.getValue().getNombre();

        opcionesMultiplesContainer.setVisible(false);
        opcionesMultiplesContainer.setManaged(false);
        ordenarContainer.setVisible(false);
        ordenarContainer.setManaged(false);
        emparejarContainer.setVisible(false);
        emparejarContainer.setManaged(false);
        completarContainer.setVisible(false);
        completarContainer.setManaged(false);

        switch(tipo) {
            case "Seleccion multiple unica respuesta":
                opcionesMultiplesContainer.setVisible(true);
                opcionesMultiplesContainer.setManaged(true);
                ayudaLabel.setText("Para preguntas de selección múltiple, marque al menos una opción como correcta.");
                break;

            case "ordenar":
                ordenarContainer.setVisible(true);
                ordenarContainer.setManaged(true);
                ayudaLabel.setText("Agregue los elementos en el orden correcto. Los estudiantes deberán reorganizarlos.");
                break;

            case "emparejar":
                emparejarContainer.setVisible(true);
                emparejarContainer.setManaged(true);
                ayudaLabel.setText("Agregue pares de conceptos que los estudiantes deberán emparejar correctamente.");
                break;

            case "completar":
                completarContainer.setVisible(true);
                completarContainer.setManaged(true);
                ayudaLabel.setText("Use ______ para marcar los espacios en blanco. Luego especifique las respuestas correctas para cada espacio.");
                break;

            case "falso y verdadero":
                verdaderoFalsoContainer.setVisible(true);
                verdaderoFalsoContainer.setManaged(true);
                ayudaLabel.setText("Marque la opción correcta (Verdadero o Falso).");
            default:
                break;
        }

        // Mostrar/ocultar el contenedor principal según sea necesario
        boolean mostrarContainer = opcionesMultiplesContainer.isVisible() ||
                ordenarContainer.isVisible() ||
                emparejarContainer.isVisible() ||
                completarContainer.isVisible() ||
                verdaderoFalsoContainer.isVisible();

        opcionesContainer.setVisible(mostrarContainer);
        opcionesContainer.setManaged(mostrarContainer);
    }

    @FXML
    private void agregarOpcion() {
        String opcion = nuevaOpcionField.getText().trim();
        if(opcion.isEmpty()) return;

        HBox opcionBox = new HBox(10);
        CheckBox correctaCheck = new CheckBox("Correcta");
        Label textoLabel = new Label(opcion);
        Button eliminarBtn = new Button("Eliminar");

        opcionBox.getChildren().addAll(correctaCheck, textoLabel, eliminarBtn);
        opcionesList.getChildren().add(opcionBox);

        eliminarBtn.setOnAction(e -> opcionesList.getChildren().remove(opcionBox));
        nuevaOpcionField.clear();
    }

    @FXML
    private void agregarElementoOrdenar() {
        String elemento = nuevoElementoOrdenarField.getText().trim();
        if(!elemento.isEmpty()) {
            ordenarList.getItems().add(elemento);
            nuevoElementoOrdenarField.clear();
        }
    }

    @FXML
    private void agregarPareja() {
        String conceptoA = nuevoConceptoAField.getText().trim();
        String conceptoB = nuevoConceptoBField.getText().trim();

        if(!conceptoA.isEmpty() && !conceptoB.isEmpty()) {
            parejas = FXCollections.observableArrayList(conceptoA, conceptoB);
            emparejarTable.getItems().add(parejas);
            nuevoConceptoAField.clear();
            nuevoConceptoBField.clear();
        }
    }

    @FXML
    private void agregarRespuestaCompletar() {
        try {
            int espacioNum = Integer.parseInt(numeroEspacioField.getText());
            String respuestas = respuestasCompletarField.getText().trim();

            if(!respuestas.isEmpty()) {
                ObservableList<String> fila = FXCollections.observableArrayList(
                        String.valueOf(espacioNum),
                        respuestas
                );
                respuestasCompletarTable.getItems().add(fila);
                numeroEspacioField.clear();
                respuestasCompletarField.clear();
            }
        } catch(NumberFormatException e) {
            mostrarAlerta("Error", "El número de espacio debe ser un valor numérico.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String obtenerRespuestaCorrecta() {
        for (Node nodo : opcionesList.getChildren()) {
            if (nodo instanceof HBox) {
                HBox opcionBox = (HBox) nodo;
                CheckBox checkBox = null;
                Label label = null;

                for (Node subnodo : opcionBox.getChildren()) {
                    if (subnodo instanceof CheckBox) {
                        checkBox = (CheckBox) subnodo;
                    } else if (subnodo instanceof Label) {
                        label = (Label) subnodo;
                    }
                }

                if (checkBox != null && checkBox.isSelected() && label != null) {
                    return label.getText();
                }
            }
        }

        return null;
    }
    private List<OpcionRespuesta> obtenerOpcionesConEstado() {
        List<OpcionRespuesta> lista = new ArrayList<>();

        for (Node nodo : opcionesList.getChildren()) {
            if (nodo instanceof HBox) {
                HBox opcionBox = (HBox) nodo;
                CheckBox checkBox = null;
                Label label = null;

                for (Node subnodo : opcionBox.getChildren()) {
                    if (subnodo instanceof CheckBox) {
                        checkBox = (CheckBox) subnodo;
                    } else if (subnodo instanceof Label) {
                        label = (Label) subnodo;
                    }
                }
                String esCorrecta="N";
                if (label != null && checkBox != null) {
                    if(checkBox.isSelected()){
                        esCorrecta="S";
                    }
                    lista.add(new OpcionRespuesta(label.getText(), esCorrecta));
                }
            }
        }

        return lista;
    }


}
