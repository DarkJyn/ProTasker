package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import protasker.Model.Project;
import protasker.Model.User;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectScreenController{
    @FXML
    private Label overviewLabelInDashBoard;

    @FXML
    private Label profileScreen;
    @FXML
    private VBox vbox;
    @FXML
    private Label taskScreen;


    private User currentUser;
    private ArrayList<Project> projects = new ArrayList<>();
    public void setCurrentUser(User currentUser) throws IOException {
        this.currentUser = currentUser;
        projects = currentUser.getProjects();
        System.out.println("set user in project screen successful");
        loadProjects();
    }

    private void loadProjects() throws IOException {
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ProjectScreen/project-hbox.fxml"));
            HBox hbox = loader.load();
            ProjectHboxController controller = loader.getController();
            controller.setData(project);
            vbox.getChildren().add(hbox);
        }
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
    private Label logOut;
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/login-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    void onProfileScreenClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) profileScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/profile-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    void onTaskClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) taskScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/TaskScreen/task-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Button newProjectButton;
    @FXML
    void onNewProjectButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/new-project.fxml"));
        Parent root = fxmlLoader.load();

        NewProjectController controller = fxmlLoader.getController();
        controller.setCurrentUser(currentUser);
        controller.setProjectScreenController(this);

        Stage stage = new Stage(); // Tạo cửa sổ mới
        stage.setTitle("New Project");
        stage.setScene(new Scene(root, 665, 300)); // Đặt kích thước cửa sổ
        stage.show();
    }
}
