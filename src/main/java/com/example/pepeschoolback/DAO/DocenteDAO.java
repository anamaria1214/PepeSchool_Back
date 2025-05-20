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

    public int agregarPregunta(int id, String descripcion, String respuesta, int estado_id, int pregunta_id,
                                int dificultad_id, int tema_id, int tipopregunta_id, int visibilidad_id) {
        int resultado = 0;

        String call = "{ ? = call  crear_pregunta(?,?,?,?,?,?,?,?,?) }";

        try (Connection con = oracleConnector.getConnection();
             CallableStatement stmt = con.prepareCall(call)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2,id);
            stmt.setString(3, descripcion);
            stmt.setString(4, respuesta);
            stmt.setInt(5, estado_id);
            stmt.setInt(6, pregunta_id);
            stmt.setInt(7, dificultad_id);
            stmt.setInt(8, tema_id);
            stmt.setInt(9, tipopregunta_id);
            stmt.setInt(10, visibilidad_id);

            stmt.execute();

            resultado= stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public int recuperarID(String nombre){
        int resultado = 0;

        String call = "{ ? = call devolver_Pregunta(?) }";

        try (Connection con = oracleConnector.getConnection();
             CallableStatement stmt = con.prepareCall(call)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, nombre);
            stmt.execute();

            resultado = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }


    public void agregarOpciones(String descrip, String respuestacorrecta, int pregunta_id){
        String sql="INSERT INTO OPCION(DESCRIPCION, RESPUESTACORRECTA, PREGUNTA_ID) VALUES ('"+descrip+"','"+respuestacorrecta+"',"+pregunta_id+")";
        oracleConnector.realizarConsulta(sql);
    }



}
