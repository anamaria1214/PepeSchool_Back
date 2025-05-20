package com.example.pepeschoolback.config;

import java.sql.*;
import java.util.Properties;

public class OracleConnector {

    private String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private String user = "C##ANAUSER";
    private String password = "12345";
    private Connection con;

    public Connection getConnection() {
        return con;
    }
    public void connect() {
        try {
            Properties info = new Properties();
            info.put("user", user);
            info.put("password", password);
            //info.put("internal_logon", "SYSDBA");

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

    public int ejecutarUpdate(String sql, Object... parametros) {
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            for (int i = 0; i < parametros.length; i++) {
                stmt.setObject(i + 1, parametros[i]);
            }

            return stmt.executeUpdate(); // devuelve número de filas afectadas
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
