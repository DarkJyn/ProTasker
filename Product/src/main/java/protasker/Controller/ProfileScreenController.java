package protasker.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import protasker.Model.User;

import java.io.IOException;

public class ProfileScreenController {

    @FXML
    private Label overviewLabelInDashBoard;

    private User currentUser;
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        System.out.println("set user in dash board succesful");
    }

    @FXML
    void onOverviewClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/dash-board.fxml"));
        Parent root = loader.load();
        DashBoardController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) overviewLabelInDashBoard.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    private Label projectScreen;

    @FXML
    private Label taskScreen;

    @FXML
    private Label logOut;
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/login-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    void onProjectClick(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-screen.fxml"));
        Parent root = loader.load();
        ProjectScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) projectScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    void onTaskClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TaskScreen/task-screen.fxml"));
        Parent root = loader.load();
        TaskScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) taskScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    private ImageView avatarUser;
    @FXML
    void initialize() {
        assert avatarUser != null : "fx:id=\"avatarUser\" was not injected: check your FXML file 'project-screen.fxml'.";
        Rectangle rect = new Rectangle(200,200);
        avatarUser.setClip(rect);
    }
}
