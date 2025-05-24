package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.DocenteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.vo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FormularioPregunta implements Initializable{
    @FXML
    private TextField pesoPregunta1;
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
    private TextArea textoCompletarField;

    @FXML
    private ComboBox<Estado> estadoCombo;

    @FXML
    private ComboBox<Dificultad> dificultadCombo;

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
    @FXML private TextField nuevoConceptoAField;
    @FXML private TextField nuevoConceptoBField;

    @FXML private VBox completarContainer;
    @FXML private TableView<ObservableList<String>> respuestasCompletarTable;
    @FXML private TextField numeroEspacioField;
    @FXML private TextField respuestasCompletarField;

    @FXML private VBox opcionesMultiplesContainer;
    @FXML private Label ayudaLabel;

    @FXML private CheckBox verdadero;
    @FXML private CheckBox falso;

    private ObservableList<String> opciones = FXCollections.observableArrayList();
    private ObservableList<Boolean> opcionesCorrectas = FXCollections.observableArrayList();
    @FXML
    private TableView<Pareja> emparejarTable;
    @FXML
    private TableColumn<Pareja, String> conceptoAColumn;
    @FXML
    private TableColumn<Pareja, String> conceptoBColumn;
    private ObservableList<Pareja> parejasList = FXCollections.observableArrayList();
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
            conceptoAColumn.setCellValueFactory(cellData -> cellData.getValue().conceptoAProperty());
            conceptoBColumn.setCellValueFactory(cellData -> cellData.getValue().conceptoBProperty());
            emparejarTable.setItems(parejasList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void agregarPregunta() throws Exception {
        String tipo = tipoPreguntaCombo.getValue().getNombre();
        String respuesta= "";
        int idPregunta=0;
        switch(tipo) {
            case "Seleccion multiple unica respuesta":
                respuesta=obtenerRespuestaCorrecta();
                try{
                    idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                            dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                            tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId(), Integer.parseInt(pesoPregunta1.getText()), UsuarioActivo.getInstance().getUserId());
                    JOptionPane.showMessageDialog(null,"Se agregó la pregunta correctamente");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
                for(int i=0;i< obtenerOpcionesConEstado().size();i++){
                    docenteDAO.crearOpcion(0,obtenerOpcionesConEstado().get(i).getTexto(), obtenerOpcionesConEstado().get(i).getEsCorrecta(),idPregunta);
                }
                break;

            case "ordenar":
                respuesta=obtenerElementosOrdenados();
                try{
                    idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                            dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                            tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId(), Integer.parseInt(pesoPregunta1.getText()), UsuarioActivo.getInstance().getUserId());
                    JOptionPane.showMessageDialog(null,"Se agregó la pregunta correctamente");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
                break;

            case "emparejar":
                respuesta=obtenerParejasFormateadas();
                try{
                    idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                            dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                            tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId(), Integer.parseInt(pesoPregunta1.getText()), UsuarioActivo.getInstance().getUserId());
                    JOptionPane.showMessageDialog(null,"Se agregó la pregunta correctamente");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
                break;

            case "completar":
                respuesta=textoCompletarField.getText();
                try{
                    idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                            dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                            tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId(), Integer.parseInt(pesoPregunta1.getText()), UsuarioActivo.getInstance().getUserId());
                    JOptionPane.showMessageDialog(null,"Se agregó la pregunta correctamente");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
              break;

            case "falso y verdadero":
                if(verdadero.isSelected()){
                    respuesta="verdadero";
                }else {
                    respuesta="falso";
                }
                idPregunta=0;
                try{
                    idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                            dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                            tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId(), Integer.parseInt(pesoPregunta1.getText()), UsuarioActivo.getInstance().getUserId());
                    JOptionPane.showMessageDialog(null,"Se agregó la pregunta correctamente");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
                if(respuesta.equals("verdadero")){
                    docenteDAO.crearOpcion(0,"verdadero", "S",idPregunta);
                    docenteDAO.crearOpcion(0,"falso", "N",idPregunta);
                }else{
                    docenteDAO.crearOpcion(0,"verdadero", "N",idPregunta);
                    docenteDAO.crearOpcion(0,"falso", "S",idPregunta);
                }
                break;
            case "Seleccion multiple multiples respuestas":
                respuesta=obtenerRespuestasCorrectas();
                idPregunta=0;
                try{
                    idPregunta=docenteDAO.agregarPregunta(0, pregunta.getText(), respuesta, estadoCombo.getValue().getId(),
                            dificultadCombo.getValue().getId(), temaCombo.getValue().getId(),
                            tipoPreguntaCombo.getValue().getId(), visibilidadCombo.getValue().getId(), Integer.parseInt(pesoPregunta1.getText()), UsuarioActivo.getInstance().getUserId());
                    JOptionPane.showMessageDialog(null,"Se agregó la pregunta correctamente");
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(idPregunta==0){
                    JOptionPane.showMessageDialog(null,"No se agregó la pregunta");
                }
                System.out.println(idPregunta);
                for(int i=0;i< obtenerOpcionesConEstado().size();i++){
                    docenteDAO.crearOpcion(0,obtenerOpcionesConEstado().get(i).getTexto(), obtenerOpcionesConEstado().get(i).getEsCorrecta(),idPregunta);
                }
                break;

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
            case "Seleccion multiple unica respuesta", "Seleccion multiple multiples respuestas":
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
                break;

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

    private String obtenerElementosOrdenados() {
        // Obtener los items del ListView
        ObservableList<String> items = ordenarList.getItems();

        // Unir los elementos con guiones
        return String.join("-", items);
    }

    @FXML
    private void agregarPareja() {
        String conceptoA = nuevoConceptoAField.getText().trim();
        String conceptoB = nuevoConceptoBField.getText().trim();

        if(!conceptoA.isEmpty() && !conceptoB.isEmpty()) {
            Pareja nuevaPareja = new Pareja(conceptoA, conceptoB);
            parejasList.add(nuevaPareja);
            nuevoConceptoAField.clear();
            nuevoConceptoBField.clear();
        }
    }

    private String obtenerParejasFormateadas() {
        return parejasList.stream()
                .map(p -> p.getConceptoA() + "-" + p.getConceptoB())
                .collect(Collectors.joining(","));
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

    private String obtenerRespuestasCorrectas() {
        StringBuilder respuestasCorrectas = new StringBuilder();

        for (Node nodo : opcionesList.getChildren()) {
            if (nodo instanceof HBox) {
                HBox opcionBox = (HBox) nodo;
                CheckBox checkBox = null;
                Label label = null;

                // Buscar los componentes CheckBox y Label en el HBox
                for (Node subnodo : opcionBox.getChildren()) {
                    if (subnodo instanceof CheckBox) {
                        checkBox = (CheckBox) subnodo;
                    } else if (subnodo instanceof Label) {
                        label = (Label) subnodo;
                    }
                }

                // Si está marcado como correcto y tiene texto, agregarlo
                if (checkBox != null && checkBox.isSelected() && label != null) {
                    if (!respuestasCorrectas.isEmpty()) {
                        respuestasCorrectas.append(",");
                    }
                    respuestasCorrectas.append(label.getText());
                }
            }
        }

        return respuestasCorrectas.toString();
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
