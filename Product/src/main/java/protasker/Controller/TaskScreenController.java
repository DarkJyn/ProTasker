package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskScreenController {
    @FXML
    private Label overviewLabelInDashBoard;

    @FXML
    private Label profileScreen;

    @FXML
    private Label projectScreen;
    private User currentUser;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    public void setCurrentUser(User currentUser) throws IOException {
        this.currentUser = currentUser;
        tasks = currentUser.getTasksList();
        projects = currentUser.getProjects();
        System.out.println("set user in dash board succesful");
        loadTasks();
    }
    @FXML
    private VBox vbox;
    public void loadTasks() throws IOException {
        vbox.getChildren().clear();
        for (Task task : tasks){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/TaskScreen/task.fxml"));
            VBox vboxItem = loader.load();
            TaskController controller = loader.getController();
            controller.setCurrentProject(currentUser);
            controller.setData(task);
            vbox.getChildren().add(vboxItem);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/profile-screen.fxml"));
        Parent root = loader.load();
        ProfileScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) profileScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    void onProjectScreenClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-screen.fxml"));
        Parent root = loader.load();
        ProjectScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) projectScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    private Button newTaskButton;
    @FXML
    void onNewTaskButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/TaskScreen/new-task.fxml"));
        Parent root = fxmlLoader.load();
        NewTaskController controller = fxmlLoader.getController();
        controller.setCurrentUser(currentUser);
        controller.setTaskScreenController(this);
        controller.setParentProject(projects);
        Stage stage = new Stage(); // Tạo cửa sổ mới
        stage.setTitle("New Task");
        stage.setScene(new Scene(root, 665, 300)); // Đặt kích thước cửa sổ
        stage.show();
    }


    public void onShowAllTaskButton(ActionEvent actionEvent) throws IOException {
        loadTasks();
    }

    public void onShowActiveTaskButton(ActionEvent actionEvent) throws IOException {
        vbox.getChildren().clear();
        if(tasks!= null) {
            for (Task task : tasks){
                if(task.getStatus().equals("In Progress")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/TaskScreen/task.fxml"));
                    VBox vboxItem = loader.load();
                    TaskController controller = loader.getController();
                    controller.setCurrentProject(currentUser);
                    controller.setData(task);
                    vbox.getChildren().add(vboxItem);
                }
            }
        }
    }

    public void onShowDoneTaskButton(ActionEvent actionEvent) throws IOException {
        vbox.getChildren().clear();
        if(tasks!= null) {
            for (Task task : tasks){
                if(task.getStatus().equals("Done")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/TaskScreen/task.fxml"));
                    VBox vboxItem = loader.load();
                    TaskController controller = loader.getController();
                    controller.setCurrentProject(currentUser);
                    controller.setData(task);
                    vbox.getChildren().add(vboxItem);
                }
            }
        }
    }
}
