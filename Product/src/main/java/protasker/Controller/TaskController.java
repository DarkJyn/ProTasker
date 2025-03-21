package protasker.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import protasker.Model.FileContact;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;

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
        File file = new File(userOwn.getUserAvatarPath());
        Image image = new Image(file.toURI().toString());
        userAvaPath.setImage(image);
//        userAvaPath.setImage(new Image(getClass().getResourceAsStream(task.getUserOwn().getUserAvatarPath())));
        taskProjectNameOwn.setText(task.getProjectOwn());
        taskStatus.valueProperty().addListener((obs, oldValue, newValue) -> {
            task.setStatus(newValue); // Cập nhật status trong Task
            taskStatus.setPromptText(newValue); // Hiển thị trạng thái mới
            FileContact.saveUsersToJson(userOwn);
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
