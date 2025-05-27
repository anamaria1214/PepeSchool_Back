package com.example.pepeschoolback.DAO;

import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.modelo.documentos.Pregunta;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;

public class EstudianteDAO {

    private final OracleConnector oracleConnector;

    public EstudianteDAO(OracleConnector oracleConnector) {
        this.oracleConnector = oracleConnector;
    }

    public int calificarPregunta(Integer userId, int examenId, int preguntaId, String enunciado) {
        try {
            String sql = "SELECT " +
                    "calificar_pregunta(" + userId + ", " + examenId + ", " + preguntaId + ", " + enunciado + ") " +
                    "FROM dual";
            ResultSet rs = oracleConnector.realizarConsulta(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al calificar la pregunta: " + e.getMessage(), e);
        }
        return 0;
    }

    public int calificarExamen(Integer userId, int examenId) {
        try {
            // Llamar como procedimiento, no como función en SELECT
            CallableStatement cs = oracleConnector.getConnection().prepareCall("{call calificar_examen(?, ?, ?)}");
            cs.setInt(1, userId);
            cs.setInt(2, examenId);
            cs.registerOutParameter(3, java.sql.Types.INTEGER);
            cs.execute();
            return cs.getInt(3);
        } catch (Exception e) {
            throw new RuntimeException("Error al calificar el examen: " + e.getMessage(), e);
        }
    }
}
