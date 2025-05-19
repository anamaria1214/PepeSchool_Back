package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.config.OracleConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class LoginControlador {

    @FXML
    private Button btnIngresar;

    @FXML
    private PasswordField contrasenia;

    @FXML
    private RadioButton esDocente;

    @FXML
    private RadioButton esEstudiante;

    @FXML
    private TextField nombreUsuario;

    //private final OracleConnector oracleConnector;

    /*public LoginControlador(OracleConnector oracleConnector) {
        this.oracleConnector = oracleConnector;
    }*/


    @FXML
    void ingresar(ActionEvent event) {
        /*ResultSet resultado;
        if(esDocente.isSelected()){
            resultado= oracleConnector.realizarConsulta("Select * from Docente where nombreusuario="+
                    nombreUsuario.getText()+" AND contrasena="+contrasenia.getText());
        }else{
            resultado= oracleConnector.realizarConsulta("Select * from Docente where nombreusuario="+
                    nombreUsuario.getText()+" AND contrasena="+contrasenia.getText());
        }*/


    }

}

