package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {
    @FXML
    private Label taskLabelInDashBoard;

    @FXML
    void ontaskLabelInDashBoardClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) taskLabelInDashBoard.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("task-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    private Label logOut;

    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Label profileLabelInDashBoard;
    @FXML
    void onProfileInDashBoardClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) profileLabelInDashBoard.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("profile-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Label projectLabelInDashBoard;


    @FXML
    void onProjectInDashBoardClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) projectLabelInDashBoard.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("project-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
}
