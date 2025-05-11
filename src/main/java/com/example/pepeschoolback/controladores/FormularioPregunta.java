package com.example.pepeschoolback.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormularioPregunta {
    @FXML
    private Button cancelarBtn;

    @FXML
    private ComboBox<String> dificultadCombo;

    @FXML
    private CheckBox esCorrectaCheck;

    @FXML
    private ComboBox<String> estadoCombo;

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
    private ComboBox<String> preguntaPadreCombo;

    @FXML
    private ComboBox<String> temaCombo;

    @FXML
    private ComboBox<String> tipoPreguntaCombo;

    @FXML
    private ComboBox<String> visibilidadCombo;

    // Controles para ordenar elementos
    @FXML private VBox ordenarContainer;
    @FXML private ListView<String> ordenarList;
    @FXML private TextField nuevoElementoOrdenarField;

    // Controles para emparejar conceptos
    @FXML private VBox emparejarContainer;
    @FXML private TableView<ObservableList<String>> emparejarTable;
    @FXML private TextField nuevoConceptoAField;
    @FXML private TextField nuevoConceptoBField;

    // Controles para completar espacios
    @FXML private VBox completarContainer;
    @FXML private TableView<ObservableList<String>> respuestasCompletarTable;
    @FXML private TextField numeroEspacioField;
    @FXML private TextField respuestasCompletarField;

    //Para respuesta normal
    @FXML private TextField respuestaField;
    @FXML private VBox respuestaCorta;

    @FXML private VBox opcionesMultiplesContainer;
    @FXML private Label ayudaLabel;

    private ObservableList<String> opciones = FXCollections.observableArrayList();
    private ObservableList<Boolean> opcionesCorrectas = FXCollections.observableArrayList();

    @FXML
    private void cambiarTipoPregunta() {
        String tipo = tipoPreguntaCombo.getValue();

        // Ocultar todos los sub-contenedores primero
        opcionesMultiplesContainer.setVisible(false);
        opcionesMultiplesContainer.setManaged(false);
        ordenarContainer.setVisible(false);
        ordenarContainer.setManaged(false);
        emparejarContainer.setVisible(false);
        emparejarContainer.setManaged(false);
        completarContainer.setVisible(false);
        completarContainer.setManaged(false);

        // Mostrar solo el contenedor necesario
        switch(tipo) {
            case "Selección múltiple":
            case "Verdadero/Falso":
                opcionesMultiplesContainer.setVisible(true);
                opcionesMultiplesContainer.setManaged(true);
                ayudaLabel.setText(tipo.equals("Verdadero/Falso") ?
                        "Marque la opción correcta (Verdadero o Falso)." :
                        "Para preguntas de selección múltiple, marque al menos una opción como correcta.");
                break;

            case "Ordenar elementos":
                ordenarContainer.setVisible(true);
                ordenarContainer.setManaged(true);
                ayudaLabel.setText("Agregue los elementos en el orden correcto. Los estudiantes deberán reorganizarlos.");
                break;

            case "Emparejar conceptos":
                emparejarContainer.setVisible(true);
                emparejarContainer.setManaged(true);
                ayudaLabel.setText("Agregue pares de conceptos que los estudiantes deberán emparejar correctamente.");
                break;

            case "Completar espacios":
                completarContainer.setVisible(true);
                completarContainer.setManaged(true);
                ayudaLabel.setText("Use ______ para marcar los espacios en blanco. Luego especifique las respuestas correctas para cada espacio.");
                break;

            case "Respuesta corta":
                respuestaCorta.setVisible(true);
                respuestaCorta.setManaged(true);
                ayudaLabel.setText("Agregue la respuesta única de la pregunta");
                break;


            default:
                break;
        }

        // Mostrar/ocultar el contenedor principal según sea necesario
        boolean mostrarContainer = opcionesMultiplesContainer.isVisible() ||
                ordenarContainer.isVisible() ||
                emparejarContainer.isVisible() ||
                completarContainer.isVisible() ||
                respuestaCorta.isVisible();

        opcionesContainer.setVisible(mostrarContainer);
        opcionesContainer.setManaged(mostrarContainer);
    }

    private void configurarOpcionesBasicas(String tipo) {
        opcionesList.getChildren().clear();
        if(tipo.equals("Verdadero/Falso")) {
            CheckBox verdadero = new CheckBox("Verdadero");
            CheckBox falso = new CheckBox("Falso");
            opcionesList.getChildren().addAll(verdadero, falso);
        }
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
            ObservableList<String> pareja = FXCollections.observableArrayList(conceptoA, conceptoB);
            emparejarTable.getItems().add(pareja);
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

}
