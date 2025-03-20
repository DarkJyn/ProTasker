package protasker.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import protasker.Model.User;
import java.io.IOException;
import java.util.List;

public class DashBoardController {
    private User currentUser;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        System.out.println("set user in dash board succesful");
    }

    @FXML
    private Label taskLabelInDashBoard;
    @FXML
    void ontaskLabelInDashBoardClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TaskScreen/task-screen.fxml"));
        Parent root = loader.load();
        TaskScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) taskLabelInDashBoard.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Label logOut;
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/login-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Label profileLabelInDashBoard;
    @FXML
    void onProfileInDashBoardClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/profile-screen.fxml"));
        Parent root = loader.load();
        ProfileScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) profileLabelInDashBoard.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Label projectLabelInDashBoard;
    @FXML
    void onProjectInDashBoardClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-screen.fxml"));
        Parent root = loader.load();
        ProjectScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) projectLabelInDashBoard.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }
}
