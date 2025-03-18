package protasker.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import protasker.Model.Project;
import protasker.Model.Task;

public class TaskController {

    @FXML
    private Label projectOfTaskName;

    @FXML
    private VBox taskBG;
    @FXML
    private ImageView userAvaPath;
    @FXML
    private Label taskDecrip;

    @FXML
    private Label taskProjectNameOwn;

    @FXML
    private Label taskName;

    @FXML
    private Label taskStatus;

    @FXML
    private ImageView taskUserAvatar;
    void setData(Task task) {
        taskName.setText(task.getName());
        taskDecrip.setText(task.getDescription());
        taskStatus.setText(task.getStatus());
        userAvaPath.setImage(task.getUserOwn().getUserAvatarPath());
        taskProjectNameOwn.setText(task.getProjectOwn().getName());
    }
}
