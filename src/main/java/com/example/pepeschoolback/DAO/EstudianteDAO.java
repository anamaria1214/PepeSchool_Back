package com.example.pepeschoolback.DAO;

import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.modelo.documentos.Pregunta;

import java.util.List;

public class EstudianteDAO {

    private final OracleConnector oracleConnector;

    public EstudianteDAO(OracleConnector oracleConnector) {
        this.oracleConnector = oracleConnector;
    }
}
