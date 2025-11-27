package protasker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import protasker.Model.FileContact;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;
import protasker.Model.DataStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static protasker.Model.Authenticator.FILE_PATH;

public class NewTaskController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private ComboBox<Project> parentProject;

    @FXML
    private ComboBox<User> assigneeComboBox;

    @FXML
    private ComboBox<String> statusOfTask;

    @FXML
    private TextArea taskDescription;

    @FXML
    private ComboBox<String> taskPriority;

    @FXML
    private TextField taskTitle;
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

        // Thêm listener để load members khi chọn project
        parentProject.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    loadProjectMembers(newVal);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadProjectMembers(Project project) throws IOException {
        DataStore dataStore = FileContact.loadDataStore();
        List<User> members = dataStore.getProjectMembers(project.getProjectId());
        ObservableList<User> observableList = FXCollections.observableArrayList(members);
        assigneeComboBox.setItems(observableList);

        // Custom cell factory để hiển thị username
        assigneeComboBox.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        setText((user == null || empty) ? null : user.getUsername());
                    }
                };
            }
        });

        // Custom button cell
        assigneeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText((user == null || empty) ? null : user.getUsername());
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
        String taskName = taskTitle.getText();
        String description = taskDescription.getText();
        String priority = taskPriority.getValue();
        String status  = statusOfTask.getValue();
        Project project = parentProject.getSelectionModel().getSelectedItem();
        User assignee = assigneeComboBox.getValue();
        
        if (taskName.isEmpty() || priority == null || status == null || project == null) {
            showAlert("Error", "Please fill in the information", Alert.AlertType.ERROR);
            return;
        }
        
        if (assignee == null) {
            showAlert("Error", "Please select an assignee", Alert.AlertType.ERROR);
            return;
        }
        
        DataStore dataStore = FileContact.loadDataStore();
        Task task = new Task(taskName, description, status, project.getProjectId(), assignee.getUserId(), priority);
        dataStore.getTasks().add(task);
        FileContact.saveDataStore(dataStore);
        
        if(taskScreenController != null) taskScreenController.refreshTasks();
        if(projectDetailController != null) projectDetailController.refreshAllTasks();
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
