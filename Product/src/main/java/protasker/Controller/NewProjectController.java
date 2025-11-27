package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import protasker.Model.FileContact;
import protasker.Model.Project;
import protasker.Model.User;
import protasker.Model.DataStore;

import java.io.IOException;
import java.net.Authenticator;
import java.util.List;

import static protasker.Model.Authenticator.FILE_PATH;

public class NewProjectController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private ComboBox<String> priorityOfProject;

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
        
        // Kiểm tra Target Date không được trước Start Date
        if (targerDateOfProject.getValue().isBefore(startDateOfProject.getValue())) {
            showAlert("Error", "Target Date cannot be before Start Date", Alert.AlertType.ERROR);
            return;
        }
        
        DataStore dataStore = FileContact.loadDataStore();
        for(Project project : dataStore.getUserProjects(currentUser.getUserId())) {
            if (project.getName().equals(projectName)) {
                showAlert("Error", "Project's Name used", Alert.AlertType.ERROR);
                return;
            }
        }
        
        Project newProject = new Project(projectName, priority, description, currentUser.getUserId(), startDate, targetDate);
        dataStore.getProjects().add(newProject);
        dataStore.addUserToProject(currentUser.getUserId(), newProject.getProjectId(), "owner");
        FileContact.saveDataStore(dataStore);
        projectScreenController.refreshProjects();
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
