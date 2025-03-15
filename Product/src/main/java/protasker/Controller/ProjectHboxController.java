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
import protasker.Model.Project;
import protasker.Model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    User user;

    public static String formatDate(String inputDate) {
        // Định dạng ban đầu
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Định dạng mong muốn
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd");

        // Chuyển đổi chuỗi thành LocalDate
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        // Định dạng lại thành "MMM dd"
        return date.format(outputFormatter);
    }
    void setProject(Project project) {
        System.out.println(project == null);
        this.project = project;
        System.out.println("set project hbox done");
    }
    void setData(Project project) {
        projectNameLabel.setText(project.getName());
        priorityLabel.setText(project.getPriority());
        targetDateLabel.setText(formatDate(project.getTargetDate()));
        progressLabel.setText(project.getProgress() + "%");
        avatarImg.setImage(project.getLeader().getUserAvatarPath());
        System.out.println("set project data done");
    }

    @FXML
    void onProjectHboxClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-detail.fxml"));
        Parent root = loader.load();
        ProjectDetailController controller = loader.getController();
        controller.setCurrentUser(project.getLeader());
        controller.setProject(project);
        Stage stage = (Stage) projectHbox.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }
}
