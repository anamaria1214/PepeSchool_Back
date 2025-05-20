package com.example.pepeschoolback.DAO;

import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.modelo.vo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListasDAO {

    private final OracleConnector oracleConnector;

    public ListasDAO(OracleConnector oracleConnector) {
        this.oracleConnector = oracleConnector;
    }

    public List<Dificultad> obtenerTodosDificultad() throws SQLException {
        List<Dificultad> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM DIFICULTAD";

            ResultSet rs = oracleConnector.realizarConsulta(sql);
            while (rs.next()) {
                Dificultad dificultad = new Dificultad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(dificultad);
            }

        return lista;
    }

    public List<Categoria> obtenerTodosCategoria() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM CATEGORIA";

            ResultSet rs = oracleConnector.realizarConsulta(sql);
            while (rs.next()) {
                Categoria tipo = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(tipo);
            }

        return lista;
    }
    public List<Visibilidad> obtenerTodosVisibilidad() throws SQLException {
        List<Visibilidad> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM VISIBILIDAD";

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Visibilidad tipo = new Visibilidad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
            );
            lista.add(tipo);
        }

        return lista;
    }

    public List<Estado> obtenerTodosEstado() throws SQLException {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM ESTADO";

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Estado tipo = new Estado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
            );
            lista.add(tipo);
        }

        return lista;
    }

    public List<Tema> obtenerTodosTema() throws SQLException {
        List<Tema> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM TEMA";

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Tema tipo = new Tema(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
            );
            lista.add(tipo);
        }

        return lista;
    }

    public List<TipoPregunta> obtenerTodosTipoPregunta() throws SQLException {
        List<TipoPregunta> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM TIPOPREGUNTA";

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            TipoPregunta tipo = new TipoPregunta(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
            );
            lista.add(tipo);
        }

        return lista;
    }


}
