package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileScreenController {

    @FXML
    private Label overviewLabelInDashBoard;

    @FXML
    void onOverviewClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) overviewLabelInDashBoard.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("task-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    private Label projectScreen;

    @FXML
    private Label taskScreen;

    @FXML
    private Label logOut;
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    void onProjectClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) projectScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("task-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    void onTaskClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) taskScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("task-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    private ImageView avatarUser;
    @FXML
    void initialize() {
        assert avatarUser != null : "fx:id=\"avatarUser\" was not injected: check your FXML file 'project-screen.fxml'.";
        Rectangle rect = new Rectangle(200,200);
        avatarUser.setClip(rect);
    }
}
