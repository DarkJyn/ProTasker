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
import protasker.Model.DataStore;
import protasker.Model.FileContact;
import protasker.Model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
    private DataStore dataStore;
    private ArrayList<Project> projects = new ArrayList<>();

    public void setCurrentUser(User currentUser) throws IOException {
        this.currentUser = currentUser;
        this.dataStore = FileContact.loadDataStore();
        projects = (ArrayList<Project>) dataStore.getUserProjects(currentUser.getUserId());
        System.out.println("set user in project screen successful");
        loadProjects();
    }
    public void loadProjects() throws IOException {
        vbox.getChildren().clear();
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ProjectScreen/project-hbox.fxml"));
            HBox hbox = loader.load();
            System.out.println("test");
            ProjectHboxController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProject(project);
            controller.setData(project);
            vbox.getChildren().add(hbox);
        }
    }
    
    // Phương thức này reload dữ liệu từ DataStore và cập nhật UI
    public void refreshProjects() throws IOException {
        this.dataStore = FileContact.loadDataStore();
        this.projects = (ArrayList<Project>) dataStore.getUserProjects(currentUser.getUserId());
        loadProjects();
    }
    @FXML
    void onOverviewClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DashBoard/dash-board.fxml"));
        Parent root = loader.load();
        DashBoardController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) overviewLabelInDashBoard.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 750));
    }
    @FXML
    private Label logOut;
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInAndSignUp/login-screen.fxml"));
        stage.setScene(new Scene(root, 1100, 750));
    }
    @FXML
    void onProfileScreenClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/profile-screen.fxml"));
        Parent root = loader.load();
        ProfileScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) profileScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 750));
    }

    @FXML
    void onTaskClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TaskScreen/task-screen.fxml"));
        Parent root = loader.load();
        TaskScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) taskScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 750));
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

    public void onTargetDateButtonClick(ActionEvent actionEvent) throws IOException {
        projects.sort(Comparator.comparing(Project::getDueDateAsLocalDate));
        loadProjects();
    }

    public void onProgressButtonClick(ActionEvent actionEvent) throws IOException {
        projects.sort(Comparator.comparingInt(p -> p.getProgressAsInt((ArrayList<Task>)dataStore.getProjectTasks(p.getProjectId()))));
        loadProjects();
    }
    public void onPriorityButtonClick(ActionEvent actionEvent) throws IOException {
        projects.sort(Comparator.comparingInt(Project::getPriorityAsInt));
        loadProjects();
    }
}
