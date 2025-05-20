package com.example.pepeschoolback.DAO;

import com.example.pepeschoolback.config.OracleConnector;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginDAO {

    private final OracleConnector oracleConnector;

    public LoginDAO(OracleConnector oracleConnector) {
        this.oracleConnector = oracleConnector;
    }

    /**
     * Llama a la función fn_login y devuelve el ID del usuario si las credenciales son correctas.
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @param tipo 1 si es estudiante, otro valor si es docente
     * @return ID del usuario si es válido, o 0 si no lo es
     */
    public int login(String username, String password, int tipo) {
        int resultado = 0;

        String call = "{ ? = call fn_login(?, ?, ?) }";

        try {
            Connection con = oracleConnector.getConnection();
            CallableStatement stmt = con.prepareCall(call);

            stmt.registerOutParameter(1, Types.INTEGER); // Valor de retorno
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setInt(4, tipo);

            stmt.execute();

            resultado = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}