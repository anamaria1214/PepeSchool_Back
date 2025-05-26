package com.example.pepeschoolback.controladores;

import com.example.pepeschoolback.DAO.ListasDAO;
import com.example.pepeschoolback.DAO.LoginDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.config.UsuarioActivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


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

    private UsuarioActivo usuario = UsuarioActivo.getInstance();

    public LoginControlador(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }


    @FXML
    void ingresar(ActionEvent event) throws IOException {
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
            OracleConnector oracleConnector = new OracleConnector();
            System.out.println("Inicio de sesión exitoso. ID: " + userId);
            usuario.login(nombreUsuario.getText(), tipo, userId);
            System.out.println("Se guardó el usuario: "+usuario.getUserId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/DashboardEstudiante.fxml"));
            loader.setController(new DashboardEstudianteController());
            Parent root = loader.load();
            Stage stage= new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
        }else if(userId > 0 && tipo==2){

            OracleConnector oracleConnector = new OracleConnector();
            oracleConnector.connect();

            ListasDAO listasDAO= new ListasDAO(oracleConnector);

            System.out.println("Inicio de sesión exitoso. ID: " + userId);
            usuario.login(nombreUsuario.getText(), tipo, userId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/DashboardMaestro.fxml"));

            loader.setController(new DashboardMaestroController(listasDAO));
            Parent root = loader.load();
            Stage stage= new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
        } else {
            System.out.println("Credenciales invalidas.");
            JOptionPane.showMessageDialog(null, "Credenciales inválidas");
        }

    }

}

