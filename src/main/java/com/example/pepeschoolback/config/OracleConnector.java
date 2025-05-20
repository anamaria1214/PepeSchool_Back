package com.example.pepeschoolback.config;

import java.sql.*;
import java.util.Properties;

public class OracleConnector {

    private static OracleConnector instance;

    private String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    private String user = "SYS";
    private String password = "oracle";
    private Connection con;

    public static OracleConnector getInstance() {
        if (instance == null) {
            synchronized (OracleConnector.class) {
                if (instance == null) {
                    instance = new OracleConnector();
                }
            }
        }
        return instance;
    }

    public void connect() {
        try {
            Properties info = new Properties();
            info.put("user", user);
            info.put("password", password);
            info.put("internal_logon", "C##Anauser");

            con = DriverManager.getConnection(url, info);

            PreparedStatement stmt = con.prepareStatement("select * from visibilidad");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String descripcion = rs.getString("DESCRIPCION");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Descripcion: " + descripcion);
            }

            // Cierra la conexión
            //conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet realizarConsulta(String consulta) {
        try {
            PreparedStatement stmt = con.prepareStatement(consulta);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
