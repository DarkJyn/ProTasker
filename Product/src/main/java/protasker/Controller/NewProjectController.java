package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import protasker.Model.Project;
import protasker.Model.User;

import java.io.IOException;

public class NewProjectController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private ComboBox<String> priorityOfProject;

    @FXML
    private ComboBox<User> projectLeader;

    @FXML
    private TextField projectNameField;

    @FXML
    private DatePicker startDateOfProject;
    @FXML
    private TextArea descriptionProject;
    @FXML
    private ComboBox<String> statusOfProject;

    @FXML
    private DatePicker targerDateOfProject;
    private ProjectScreenController projectScreenController;
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public void setProjectScreenController(ProjectScreenController controller) {
        this.projectScreenController = controller;
    }
    @FXML
    void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmButtonClick(ActionEvent event) throws IOException {
        User leader = projectLeader.getSelectionModel().getSelectedItem();
        String projectName = projectNameField.getText();
        String projectLeaderName = currentUser.getUsername();
        String status = statusOfProject.getValue();
        String targetDate = targerDateOfProject.getValue().toString();
        String priority = priorityOfProject.getValue();
        String startDate = startDateOfProject.getValue().toString();
        String description = descriptionProject.getText();
        if (projectName.isEmpty()  || projectLeaderName.isEmpty() || startDate.isEmpty() || targetDate.isEmpty() || priority == null || status == null) {
            showAlert("Error", "Please fill in the information", Alert.AlertType.ERROR);
            return;
        }
        Project newProject = new Project(projectName, priority, description, leader, startDate, targetDate);
        currentUser.getProjects().add(newProject);
        projectScreenController.loadProjects();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String erlabel, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(erlabel);
        alert.showAndWait();
    }

}
