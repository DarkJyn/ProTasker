package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController{

    @FXML
    private Button logInButton;
    @FXML
    private Label signUpScreen;
    @FXML
    void onSignUpScreen(MouseEvent event) throws IOException {
        Stage stage = (Stage) signUpScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("signup-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    void onLogInButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) logInButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dash-board.fxml"));
        stage.setScene(new Scene(root, 900, 600));

    }
}