package com.example.pepeschoolback.controladores;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import com.example.pepeschoolback.modelo.documentos.Pregunta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResponderExamenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnFinalizar;

    @FXML
    private VBox boxPreguntas;

    @FXML
    void finalizarExamen(ActionEvent event) {

    }

    @FXML
    void initialize() {
        OracleConnector db = OracleConnector.getInstance();
        db.connect();

        //ResultSet consultaDefinitiva = db.realizarConsulta("SELECT * FROM examen e JOIN detalleexamenpregunta d ON e.id = p.idExamen JOIN pregunta p ON d.idPregunta = p.id WHERE idExamen = " + UsuarioActivo.getInstance().getExamenEstudianteActualId());

        try {
            ResultSet rs = db.realizarConsulta(
                    "SELECT e.cantPreguntasEstudiante, p.id AS preguntaId, p.descripcion AS enunciado, p.idTipoPregunta AS tipoPregunta, e.idTema = temaId" +
                            "FROM examen e " +
                            "JOIN detalleexamenpregunta d ON e.id = d.idExamen " +
                            "JOIN pregunta p ON d.idPregunta = p.id " +
                            "WHERE e.id = " + UsuarioActivo.getInstance().getExamenEstudianteActualId()
            );

            int cantPreguntasEstudiante = -1;
            List<Pregunta> preguntas = new ArrayList<>();

            while (rs.next()) {
                if (cantPreguntasEstudiante == -1) {
                    cantPreguntasEstudiante = rs.getInt("cantPreguntasEstudiante");
                }

                int id = rs.getInt("preguntaId");
                String enunciado = rs.getString("descripcion");
                int tipo = rs.getInt("tipoPregunta");

                preguntas.add(new Pregunta(id, enunciado, tipo));
            }
            System.out.println(preguntas);
            //Temporal mientras se deciden las preguntas aleatorias




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
