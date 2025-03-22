package protasker.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectItemController {

    @FXML
    private Label projectDesrips;

    @FXML
    private ImageView projectLeaderAvtPath;

    @FXML
    private Label projectName;

    @FXML
    private Label projectPriority;

    @FXML
    private Label projectTargetDate;

    @FXML
    private VBox vbox;

    User leader;
    Project project;
    int runningTask;
    void setCurrentUser(User user) {
        leader = user;
    }
    void setData(Project project) {
        this.project = project;
        projectName.setText(project.getName());
        projectPriority.setText(project.getPriority());
        projectDesrips.setText(project.getDescription());
        projectTargetDate.setText(formatDate(project.getTargetDate()));
        File file = new File(leader.getUserAvatarPath());
        Image image = new Image(file.toURI().toString());
        projectLeaderAvtPath.setImage(image);
    }

    public static String formatDate(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date.format(outputFormatter);
    }

    DashBoardController dashBoardController;
    void setDashBoardController(DashBoardController dashBoardController) {
        this.dashBoardController = dashBoardController;
    }

    @FXML
    public void onVboxClick(MouseEvent mouseEvent) throws IOException {
        dashBoardController.setProjectNameRightSide(project.getName());
        runningTask = 0;
        for(Task task : project.getTasks()) {
            if(task.getStatus().equals("In Progress")) {
                runningTask++;
            }
        }
        dashBoardController.setRunningTaskRightSide(runningTask +"");
        dashBoardController.setTotalTaskRightSide(project.getTasks().size()+"");
//        dashBoardController.setProgressRightSide(project.getProgress());
        int value = Integer.parseInt(project.getProgress().replace("%", ""));
        dashBoardController.updateProgress(value);
    }
}
