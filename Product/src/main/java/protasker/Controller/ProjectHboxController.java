package protasker.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import protasker.Model.Project;

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
        priorityLabel.setText(project.getPriority());
        projectNameLabel.setText(project.getName());
        targetDateLabel.setText(formatDate(project.getTargetDate()));
        progressLabel.setText(project.getProgress() + "%");

    }

}
