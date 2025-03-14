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

    void setData(Project project) {
        projectNameLabel.setText(project.getName());
        priorityLabel.setText(project.getPriority());
        targetDateLabel.setText(formatDate(project.getTargetDate()));
        progressLabel.setText(project.getProgress() + "%");
        avatarImg.setImage(project.getLeader().getUserAvatarPath());
    }
    @FXML
    void onProjectHboxClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) projectHbox.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/ProjectScreen/project-detail.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
}
