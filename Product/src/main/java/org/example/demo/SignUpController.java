package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController{
    @FXML
    private Button signUpButton;

    @FXML
    void onSignUpButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
}
