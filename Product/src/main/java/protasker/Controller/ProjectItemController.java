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
import protasker.Model.DataStore;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;
import protasker.Model.FileContact;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        
        DataStore dataStore = FileContact.loadDataStore();
        User projectLeader = dataStore.getUsers().stream()
            .filter(u -> u.getUserId().equals(project.getLeaderId()))
            .findFirst().orElse(null);
        
        if(projectLeader != null) {
            if(projectLeader.getUserAvatarPath() != null && !projectLeader.getUserAvatarPath().isEmpty()) {
                try {
                    File file = new File(projectLeader.getUserAvatarPath());
                    Image image = new Image(file.toURI().toString());
                    projectLeaderAvtPath.setImage(image);
                } catch (Exception e) {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                    projectLeaderAvtPath.setImage(defaultImage);
                }
            } else {
                Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                projectLeaderAvtPath.setImage(defaultImage);
            }
        }
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
        DataStore dataStore = FileContact.loadDataStore();
        List<Task> projectTasks = dataStore.getProjectTasks(project.getProjectId());
        
        runningTask = 0;
        for(Task task : projectTasks) {
            if(task.getStatus().equals("In Progress")) {
                runningTask++;
            }
        }
        dashBoardController.setRunningTaskRightSide(runningTask +"");
        dashBoardController.setTotalTaskRightSide(projectTasks.size()+"");
        int progress = project.getProgressAsInt((java.util.ArrayList<Task>)projectTasks);
        dashBoardController.updateProgress(progress);
    }
}
