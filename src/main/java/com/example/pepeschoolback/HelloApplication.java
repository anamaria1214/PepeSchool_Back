package com.example.pepeschoolback;

import com.example.pepeschoolback.DAO.LoginDAO;
import com.example.pepeschoolback.config.OracleConnector;
import com.example.pepeschoolback.controladores.LoginControlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        OracleConnector oracleConnector = new OracleConnector();
        oracleConnector.connect();

        LoginDAO loginDAO = new LoginDAO(oracleConnector);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pepeschoolback/views/login.fxml"));
        loader.setController(new LoginControlador(loginDAO));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Pepe School");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        OracleConnector connector = new OracleConnector();
        connector.connect();
        System.out.println(connector.realizarConsulta("Select * from visibilidad"));
        System.out.println("Camilo es muy lindo");
    }
}