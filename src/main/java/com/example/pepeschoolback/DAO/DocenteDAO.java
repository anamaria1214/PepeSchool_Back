package com.example.pepeschoolback.DAO;

import com.example.pepeschoolback.config.OracleConnector;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class DocenteDAO {
    private final OracleConnector oracleConnector;

    public DocenteDAO(OracleConnector oracleConnector) {
        this.oracleConnector = oracleConnector;
    }

    public int agregarPregunta(int id, String descripcion, String respuesta, int estado_id,
                               int dificultad_id, int tema_id, int tipopregunta_id, int visibilidad_id,
                               int pesoPregunta, int idDocente) {
        Connection con = null;
        CallableStatement stmt = null;
        int resultado = 0;

        try {
            con = oracleConnector.getConnection();
            String call = "{ ? = call crear_pregunta(?,?,?,?,?,?,?,?,?,?) }";
            stmt = con.prepareCall(call);

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, id);
            stmt.setString(3, descripcion);
            stmt.setString(4, respuesta);
            stmt.setInt(5, estado_id);
            stmt.setInt(6, dificultad_id);
            stmt.setInt(7, tema_id);
            stmt.setInt(8, tipopregunta_id);
            stmt.setInt(9, visibilidad_id);
            stmt.setInt(10, pesoPregunta);
            stmt.setInt(11, idDocente);

            stmt.execute();
            resultado = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return resultado;
        }
        return resultado;
    }

    public void crearOpcion(int id, String descripcion, String respuestaCorrecta, int preguntaId) throws Exception {

        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = oracleConnector.getConnection();
            String call = "{ call crear_opcion(?, ?, ?, ?) }";
            stmt = con.prepareCall(call);

            stmt.setInt(1, id);
            stmt.setString(2, descripcion);
            stmt.setString(3, respuestaCorrecta);
            stmt.setInt(4, preguntaId);

            stmt.execute();

        } catch (SQLException e) {
            // Manejo específico de errores basado en códigos de error
            if (e.getErrorCode() == 20001) {
                throw new Exception("Error: Valor duplicado en índice único o clave primaria.", e);
            } else if (e.getErrorCode() == 20002) {
                throw new Exception("Error: No se encontró el dato requerido.", e);
            } else if (e.getErrorCode() == 20003) {
                throw new Exception("Error: Tipo de dato incorrecto o valor fuera de rango.", e);
            } else {
                throw new Exception("Error inesperado al crear la opción: " + e.getMessage(), e);
            }
        }
    }



}
