package protasker.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import protasker.Model.DataStore;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;
import protasker.Model.FileContact;

import java.io.File;
import java.io.IOException;

public class TaskController {
    @FXML
    private ImageView userAvaPath;
    @FXML
    private Label taskDecrip;

    @FXML
    private Label taskProjectNameOwn;

    @FXML
    private Label taskAssigneeName;

    @FXML
    private Label taskName;

    @FXML
    private ComboBox<String> taskStatus;
    User userOwn;
    void setCurrentProject(User currentUser) {
        userOwn = currentUser;
    }

    TaskScreenController taskScreenController;
    void setTaskScreenController(TaskScreenController taskScreenController) {
        this.taskScreenController = taskScreenController;
    }
    ProjectDetailController projectDetailController;
    void setProjectDetailController(ProjectDetailController projectDetailController) {
        this.projectDetailController = projectDetailController;
    }

    void setData(Task task){
        taskName.setText(task.getName());
        taskDecrip.setText(task.getDescription());
        taskStatus.setPromptText(task.getStatus());
        
        DataStore dataStore = FileContact.loadDataStore();
        User assignedUser = dataStore.getUsers().stream()
            .filter(u -> u.getUserId().equals(task.getAssignedUserId()))
            .findFirst().orElse(null);
        
        if(assignedUser != null) {
            // Hiển thị tên assignee
            taskAssigneeName.setText(assignedUser.getUsername());
            
            // Hiển thị avatar
            if(assignedUser.getUserAvatarPath() != null && !assignedUser.getUserAvatarPath().isEmpty()) {
                try {
                    File file = new File(assignedUser.getUserAvatarPath());
                    Image image = new Image(file.toURI().toString());
                    userAvaPath.setImage(image);
                } catch (Exception e) {
                    // Nếu load avatar lỗi, dùng default
                    Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                    userAvaPath.setImage(defaultImage);
                }
            } else {
                // Dùng default avatar
                Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                userAvaPath.setImage(defaultImage);
            }
        }
        
        Project project = dataStore.getProjects().stream()
            .filter(p -> p.getProjectId().equals(task.getProjectId()))
            .findFirst().orElse(null);
        if(project != null) {
            taskProjectNameOwn.setText(project.getName());
        }
        
        taskStatus.valueProperty().addListener((obs, oldValue, newValue) -> {
            task.setStatus(newValue);
            taskStatus.setPromptText(newValue);
            FileContact.updateTask(dataStore, task);
            if(taskScreenController != null){
                try {
                    taskScreenController.loadTasks();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(projectDetailController != null){
                try {
                    projectDetailController.loadAllTasks();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
