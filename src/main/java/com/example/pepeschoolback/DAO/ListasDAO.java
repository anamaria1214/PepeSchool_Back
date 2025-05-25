package com.example.pepeschoolback.DAO;

import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.modelo.documentos.Examen;
import com.example.pepeschoolback.modelo.documentos.Grupo;
import com.example.pepeschoolback.modelo.documentos.Pregunta;
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
    public List<Materia> obtenerMateriasDocente(int idDocente) throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "select id, nombre, descripcion from materia where docente_id="+idDocente;

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Materia materia = new Materia(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
            );
            lista.add(materia);
        }
        return lista;
    }
    public List<Pregunta> obtenerPreguntasDocente(int idDocente) throws SQLException {
        List<Pregunta> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.descripcion,p.respuesta, p.peso, tp.nombre FROM Pregunta p JOIN TIPOPREGUNTA tp ON p.tipopregunta_id=tp.id WHERE p.docente_id="+idDocente;

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Pregunta tipo = new Pregunta(
                    rs.getInt("id"),
                    rs.getString("descripcion"),
                    rs.getString("respuesta"),
                    rs.getInt("peso"),
                    rs.getString("nombre")
            );
            lista.add(tipo);
        }

        return lista;
    }


    public List<Examen> obtenerExamenDocente(int idDocente) throws SQLException {
        List<Examen> lista = new ArrayList<>();
        String sql = "SELECT e.nombre, e.descripcion, e.cantpreguntas, e.fechapresentacion, m.nombre AS materia FROM EXAMEN e JOIN MATERIA m ON e.materia_id=m.id WHERE m.docente_id="+idDocente;

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Examen examen = new Examen(
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("cantpreguntas"),
                    rs.getDate("fechapresentacion"),
                    rs.getString("materia")
            );
            lista.add(examen);
        }

        return lista;
    }
    public List<Pregunta> obtenerPreguntastemaytipo(int tema_id, int tipopregunta_id) throws SQLException {
        List<Pregunta> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.descripcion, p.respuesta, t.nombre, p.peso FROM PREGUNTA p JOIN TEMA t ON t.id=p.tema_id where p.tema_id="+tema_id+" AND p.tipopregunta_id="+tipopregunta_id+" AND visibilidad_id=1";

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Pregunta tipo = new Pregunta(
                    rs.getInt("id"),
                    rs.getString("descripcion"),
                    rs.getString("respuesta"),
                    rs.getInt("peso"),
                    rs.getString("nombre")
            );
            lista.add(tipo);
        }

        return lista;
    }

    public List<Grupo> obtenerGrupoDoccente(int docente_id) throws SQLException {
        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT m.nombre AS materia, g.nombre AS GRUPO, h.diaclase, h.horaclase FROM Materia m JOIN Detallegrupomateria dgm on m.id=dgm.materia_id JOIN Grupo g ON dgm.grupo_id=g.id JOIN Horario h ON h.id= m.horario_id where m.docente_id="+docente_id;

        ResultSet rs = oracleConnector.realizarConsulta(sql);
        while (rs.next()) {
            Grupo tipo = new Grupo(
                    rs.getString("materia"),
                    rs.getString("grupo"),
                    rs.getString("diaclase"),
                    rs.getString("horaclase")
            );
            lista.add(tipo);
        }

        return lista;
    }


}
