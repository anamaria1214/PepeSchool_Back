package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.LoginDAO;
import com.example.pepeschoolback.config.OracleConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
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

    private final LoginDAO loginDAO;

    public LoginControlador(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }


    @FXML
    void ingresar(ActionEvent event) {
        int tipo;
        if(esDocente.isSelected()){
            tipo=2;
        } else if (esEstudiante.isSelected()) {
            tipo=1;
        } else{
            tipo=0;
        }
        int userId = loginDAO.login(nombreUsuario.getText(), contrasenia.getText(), tipo);
        if (userId > 0 && tipo==1) {
            System.out.println("Inicio de sesión exitoso. ID: " + userId);
            cambiarPantalla("DashboardEstudiante", new Stage());
        }else if(userId > 0 && tipo==2){
            System.out.println("Inicio de sesión exitoso. ID: " + userId);
            cambiarPantalla("DashboardMaestro", new Stage());
        } else {
            System.out.println("Credenciales invalidas.");
            JOptionPane.showMessageDialog(null, "Credenciales inválidas");
        }

    }

    public static void cambiarPantalla(String nombreFXML, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginControlador.class.getResource("/com/example/pepeschoolback/views/" + nombreFXML + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

