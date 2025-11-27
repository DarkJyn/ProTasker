package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
import java.util.ArrayList;

public class ProjectHboxController {

    @FXML
    private ImageView avatarImg;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private HBox projectHbox;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label targetDateLabel;

    Project project;
    User leader;
    public static String formatDate(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date.format(outputFormatter);
    }
    void setCurrentUser(User user) {
        leader = user;
    }
    void setProject(Project project) {
        System.out.println(project == null);
        this.project = project;
        DataStore dataStore = FileContact.loadDataStore();
        ArrayList<Task> tasks = (ArrayList<Task>) dataStore.getProjectTasks(project.getProjectId());
        int progress = this.project.getProgressAsInt(tasks);
        progressLabel.setText(progress + "%");
        System.out.println("set project hbox done");
    }

    void setData(Project project) {
        projectNameLabel.setText(project.getName());
        priorityLabel.setText(project.getPriority());
        targetDateLabel.setText(formatDate(project.getTargetDate()));
        
        DataStore dataStore = FileContact.loadDataStore();
        ArrayList<Task> tasks = (ArrayList<Task>) dataStore.getProjectTasks(project.getProjectId());
        int progress = project.getProgressAsInt(tasks);
        progressLabel.setText(progress + "%");
        
        User projectLeader = dataStore.getUsers().stream()
            .filter(u -> u.getUserId().equals(project.getLeaderId()))
            .findFirst().orElse(null);
        
        if(projectLeader != null) {
            if(projectLeader.getUserAvatarPath() != null && !projectLeader.getUserAvatarPath().isEmpty()) {
                try {
                    File file = new File(projectLeader.getUserAvatarPath());
                    Image image = new Image(file.toURI().toString());
                    avatarImg.setImage(image);
                } catch (Exception e) {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                    avatarImg.setImage(defaultImage);
                }
            } else {
                Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                avatarImg.setImage(defaultImage);
            }
        }
    }

    @FXML
    void onProjectHboxClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-detail.fxml"));
        Parent root = loader.load();
        ProjectDetailController controller = loader.getController();
        controller.setCurrentUser(leader);
        controller.setProject(project);
        Stage stage = (Stage) projectHbox.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 750));
    }
}
