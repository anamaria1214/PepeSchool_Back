package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.EstudianteDAO;
import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Pregunta;
import com.example.pepeschoolback.modelo.vo.OpcionRespuesta;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Responder_ExamenController {

    @FXML private Label lblTituloExamen;
    @FXML private Label lblPregunta;
    @FXML private VBox contenedorRespuestas;

    private List<Pregunta> preguntas; // Cargadas desde PL/SQL
    private Map<Integer, Object> respuestasEstudiante = new HashMap<>();
    private int indiceActual = 0;

    public void initialize() {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();
        ListasDAO listasDAO = new ListasDAO(oracleConnector);
        UsuarioActivo usuarioActivo = UsuarioActivo.getInstance();
        // Cargar preguntas desde backend (DAO que use PL/SQL)
        try {
            preguntas = listasDAO.obtenerPreguntasDelExamen(usuarioActivo.getIdExamen());
            ResultSet rs = oracleConnector.realizarConsulta("SELECT nombre FROM EXAMEN WHERE ID = " + usuarioActivo.getIdExamen());
            rs.next();
            lblTituloExamen.setText("Examen: " + rs.getString("nombre"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mostrarPregunta(indiceActual);
    }

    private List<OpcionRespuesta> obtenerOpciones(int pregunta_id) {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();
        ListasDAO listasDAO = new ListasDAO(oracleConnector);
        try {
            return listasDAO.obtenerOpcionesRespuesta(pregunta_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarPregunta(int indice) {
        contenedorRespuestas.getChildren().clear();
        Pregunta pregunta = preguntas.get(indice);

        lblPregunta.setText((indice + 1) + ". " + pregunta.getDescripcion());

        switch (pregunta.getTipo()) {
            case "Seleccion multiple unica respuesta":
                ToggleGroup grupo = new ToggleGroup();
                for (OpcionRespuesta opcion : obtenerOpciones(pregunta.getId())) {
                    RadioButton rb = new RadioButton(opcion.getTexto());
                    rb.setToggleGroup(grupo);
                    contenedorRespuestas.getChildren().add(rb);
                }
                break;

            case "Seleccion multiple multiples respuestas":
                for (OpcionRespuesta opcion : obtenerOpciones(pregunta.getId())) {
                    CheckBox cb = new CheckBox(opcion.getTexto());
                    contenedorRespuestas.getChildren().add(cb);
                }
                break;

            case "falso y verdadero":
                RadioButton rbVerdadero = new RadioButton("verdadero");
                RadioButton rbFalso = new RadioButton("falso");
                ToggleGroup grupoFV = new ToggleGroup();
                rbVerdadero.setToggleGroup(grupoFV);
                rbFalso.setToggleGroup(grupoFV);
                contenedorRespuestas.getChildren().addAll(rbVerdadero, rbFalso);
                break;

            case "completar":
                TextField tfCompletar = new TextField();
                contenedorRespuestas.getChildren().add(tfCompletar);
                break;

            case "ordenar":
                TextField tfOrdenar = new TextField();
                tfOrdenar.setPromptText("separar con coma (-) los valores a ordenar, por ejemplo: 1-2-3-4-5");
                contenedorRespuestas.getChildren().add(tfOrdenar);
                break;

            case "emparejar":
                // Emparejar puede implementarse con dos ComboBox o una tabla editable
                TextField tfEmparejar = new TextField();
                tfEmparejar.setPromptText("separar con un espacio y una coma ( - ) la pareja, por ejemplo: Goku - Vegeta");
                contenedorRespuestas.getChildren().add(tfEmparejar);
        }
    }

    @FXML
    private void siguientePregunta() {
        guardarRespuestaActual();
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();
        EstudianteDAO estudianteDAO = new EstudianteDAO(oracleConnector);
        String respuesta = (String) respuestasEstudiante.get(indiceActual);
        int peso = estudianteDAO.calificarPregunta(UsuarioActivo.getInstance().getUserId(), UsuarioActivo.getInstance().getIdExamen(),  preguntas.get(indiceActual).getId(), respuesta);
        if (indiceActual < preguntas.size() - 1) {
            indiceActual++;
            mostrarPregunta(indiceActual);
        }
        else
            new Alert(Alert.AlertType.INFORMATION, "No existen mas preguntas").show();
    }

    @FXML
    private void anteriorPregunta() {
        guardarRespuestaActual();
        // Si la respuesta de la pregunta anterior ya existe, no permitir retroceder
        if (indiceActual > 0 && !respuestasEstudiante.containsKey(preguntas.get(indiceActual - 1).getId())) {
            indiceActual--;
            mostrarPregunta(indiceActual);
        } else if (indiceActual > 0) {
            new Alert(Alert.AlertType.WARNING, "No puedes regresar, ya respondiste la pregunta anterior.").show();
        }
    }


    @FXML
    private void enviarExamen() {
        guardarRespuestaActual();
        System.out.println(respuestasEstudiante);
        OracleConnector oc = new OracleConnector();
        oc.connect();
        EstudianteDAO estudianteDAO = new EstudianteDAO(oc);
        int resultado = estudianteDAO.calificarExamen(UsuarioActivo.getInstance().getUserId(), UsuarioActivo.getInstance().getIdExamen());
        new Alert(Alert.AlertType.CONFIRMATION, "Felicidades, la nota de su examen es: " + resultado).show();
    }

    private void guardarRespuestaActual() {
        Pregunta pregunta = preguntas.get(indiceActual);
        String tipo = pregunta.getTipo().toLowerCase();
        Object respuesta = null;

        switch (tipo) {
            case "seleccion multiple unica respuesta":
                for (Node node : contenedorRespuestas.getChildren()) {
                    if (node instanceof RadioButton rb && rb.isSelected()) {
                        respuesta = rb.getText(); // Guarda el texto de la opción
                        break;
                    }
                }
                break;

            case "seleccion multiple multiples respuestas":
                List<String> seleccionadas = new ArrayList<>();
                for (Node node : contenedorRespuestas.getChildren()) {
                    if (node instanceof CheckBox cb && cb.isSelected()) {
                        seleccionadas.add(cb.getText());
                    }
                }
                respuesta = String.join(",", seleccionadas); // Lista de Strings
                break;

            case "falso y verdadero":
                for (Node node : contenedorRespuestas.getChildren()) {
                    if (node instanceof RadioButton rb && rb.isSelected()) {
                        respuesta = rb.getText().toLowerCase(); // "verdadero" o "falso"
                        break;
                    }
                }
                break;

            case "completar":
            case "ordenar":
            case "emparejar":
                for (Node node : contenedorRespuestas.getChildren()) {
                    if (node instanceof TextField tf) {
                        respuesta = tf.getText();
                        break;
                    }
                }
                break;

            default:
                System.out.println("Tipo de pregunta no reconocido: " + tipo);
                break;
        }

        if (respuesta != null) {
            respuestasEstudiante.put(pregunta.getId(), respuesta);
            System.out.println("Respuesta guardada para pregunta " + pregunta.getId() + ": " + respuesta);
        } else {
            System.out.println("No se encontró respuesta para guardar en pregunta " + pregunta.getId());
        }
    }
}
