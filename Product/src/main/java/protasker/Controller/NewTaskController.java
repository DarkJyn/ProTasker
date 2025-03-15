package protasker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;

import java.io.IOException;
import java.util.ArrayList;

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
    private ProjectDetailController projectDetailController;
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public void setTaskScreenController(TaskScreenController controller) {
        this.taskScreenController = controller;
    }
    public void setParentProject(ArrayList<Project> projects) {
        ObservableList<Project> observableList = FXCollections.observableArrayList(projects);
        parentProject.setItems(observableList);
        parentProject.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
            @Override
            public ListCell<Project> call(ListView<Project> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Project project, boolean empty) {
                        super.updateItem(project, empty);
                        setText((project == null || empty) ? null : project.getName());
                    }
                };
            }
        });

        // Hiển thị tên của Project trên nút ComboBox khi được chọn
        parentProject.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Project project, boolean empty) {
                super.updateItem(project, empty);
                setText((project == null || empty) ? null : project.getName());
            }
        });
    }
    public void setProjectDetailScreenController(ProjectDetailController controller) {
        this.projectDetailController = controller;
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmButton(ActionEvent event) throws IOException {
        User user = currentUser;
        String taskName = taskTitle.getText();
        String description = taskDescription.getText();
        String priority = taskPriority.getValue();
        String status  = statusOfTask.getValue();
        Project project = parentProject.getSelectionModel().getSelectedItem();
        if (taskName == null || priority == null || status == null || project == null) {
            showAlert("Error", "Please fill in the information", Alert.AlertType.ERROR);
            return;
        }
        Task task = new Task(taskName,description, status, project, user,priority);
        currentUser.getTasksList().add(task);
        project.getTasks().add(task);
        if(taskScreenController != null) taskScreenController.loadTasks();
        if(projectDetailController != null) projectDetailController.loadTasks();
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
