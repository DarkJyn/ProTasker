package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;

import java.io.IOException;

public class NewTaskController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private ComboBox<Project> parentProject;

    @FXML
    private ComboBox<String> statusOfTask;

    @FXML
    private TextArea taskDescription;

    @FXML
    private ComboBox<String> taskPriority;

    @FXML
    private TextField taskTitle;

    @FXML
    private ComboBox<User> taskUser;

    private TaskScreenController taskScreenController;
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public void setTaskScreenController(TaskScreenController controller) {
        this.taskScreenController = controller;
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmButton(ActionEvent event) throws IOException {
        User user = taskUser.getSelectionModel().getSelectedItem();
        String taskName = taskTitle.getText();
        String description = taskDescription.getText();
        String priority = taskPriority.getValue();
        String status  = statusOfTask.getValue();
        Project project = parentProject.getSelectionModel().getSelectedItem();
        if (taskScreenController == null || priority == null || status == null) {
            showAlert("Error", "Please fill in the information", Alert.AlertType.ERROR);
            return;
        }
        Task task = new Task(taskName,description, status, project, user,priority);
        currentUser.getTasksList().add(task);
        taskScreenController.loadTasks();
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
