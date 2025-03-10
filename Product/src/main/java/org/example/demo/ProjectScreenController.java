package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectScreenController {
    @FXML
    private Label overviewLabelInDashBoard;

    @FXML
    private Label profileScreen;

    @FXML
    private Label taskScreen;

    @FXML
    void onOverviewClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) overviewLabelInDashBoard.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dash-board.fxml"));
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
    void onProfileScreenClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) profileScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("profile-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    void onTaskClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) taskScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("task-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
}
