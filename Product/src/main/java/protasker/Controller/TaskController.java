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
        });
    }
    TaskScreenController taskScreenController;
    void setTaskScreenController(TaskScreenController taskScreenController) {
        this.taskScreenController = taskScreenController;
    }
    @FXML
    void initialize() throws IOException {
        assert taskDecrip != null : "fx:id=\"taskDecrip\" was not injected: check your FXML file 'task.fxml'.";
        assert taskName != null : "fx:id=\"taskName\" was not injected: check your FXML file 'task.fxml'.";
        assert taskProjectNameOwn != null : "fx:id=\"taskProjectNameOwn\" was not injected: check your FXML file 'task.fxml'.";
        assert taskStatus != null : "fx:id=\"taskStatus\" was not injected: check your FXML file 'task.fxml'.";
        assert userAvaPath != null : "fx:id=\"userAvaPath\" was not injected: check your FXML file 'task.fxml'.";

    }
}
