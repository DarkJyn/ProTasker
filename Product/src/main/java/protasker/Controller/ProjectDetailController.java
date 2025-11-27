package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import protasker.Model.DataStore;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;
import protasker.Model.FileContact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectDetailController {
    @FXML
    private Label logOut;

    @FXML
    private Button newTaskButton;

    @FXML
    private Button manageMembersButton;

    @FXML
    private Label overviewLabelInDashBoard;

    @FXML
    private Label profileScreen;

    @FXML
    private Label projectName;

    @FXML
    private Label projectProgress;

    @FXML
    private Label projectScreen;

    @FXML
    private Button showActiveTaskButton;

    @FXML
    private Button showAllTaskButton;

    @FXML
    private Button showDoneTaskButton;

    @FXML
    private Label numberOfAllTasks;
    @FXML
    private Label numberOfActiveTasks;
    @FXML
    private Label numberOfDoneTasks;

    @FXML
    private Label taskScreen;

    User currentUser;
    Project project;
    DataStore dataStore;
    void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    void setProject(Project project) throws IOException {
        this.project = project;
        this.dataStore = FileContact.loadDataStore();
        projectName.setText(project.getName());
        ArrayList<Task> tasks = (ArrayList<Task>) dataStore.getProjectTasks(project.getProjectId());
        int progress = project.getProgressAsInt(tasks);
        projectProgress.setText(progress + "%");
        
        loadAllTasks();
    }
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInAndSignUp/login-screen.fxml"));
        stage.setScene(new Scene(root, 1100, 750));
    }

    @FXML
    void onNewTaskButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/TaskScreen/new-task.fxml"));
        Parent root = fxmlLoader.load();
        NewTaskController controller = fxmlLoader.getController();
        controller.setCurrentUser(currentUser);
        controller.setProjectDetailScreenController(this);
        ArrayList<Project> projects = new ArrayList<>(Collections.singleton(project));
        controller.setParentProject(projects);
        Stage stage = new Stage(); // p cửa sổ mới
        stage.setTitle("New Task");
        stage.setScene(new Scene(root, 665, 250)); // Đặt kích thước cửa sổ
        stage.show();
    }
    @FXML
    private VBox vbox;

    public void loadAllTasks() throws IOException {
        int numberOfDoneTask = 0;
        int numberOfActiveTask = 0;
        vbox.getChildren().clear();
        List<Task> projectTasks = dataStore.getProjectTasks(project.getProjectId());
        for (Task task : projectTasks){
            if(task.getStatus().equals("Done")) {numberOfDoneTask++;}
            if(task.getStatus().equals("In Progress")) {numberOfActiveTask++;}
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/TaskScreen/task.fxml"));
            VBox vboxItem = loader.load();
            TaskController controller = loader.getController();
            controller.setCurrentProject(currentUser);
            controller.setData(task);
            controller.setProjectDetailController(this);
            vbox.getChildren().add(vboxItem);
        }
        numberOfAllTasks.setText(String.valueOf(projectTasks.size()));
        numberOfActiveTasks.setText(String.valueOf(numberOfActiveTask));
        numberOfDoneTasks.setText(String.valueOf(numberOfDoneTask));
        int progress = project.getProgressAsInt((ArrayList<Task>)projectTasks);
        projectProgress.setText(progress + "%");
        System.out.println(progress);
    }
    
    // Phương thức này reload dữ liệu từ DataStore và cập nhật UI
    public void refreshAllTasks() throws IOException {
        this.dataStore = FileContact.loadDataStore();
        loadAllTasks();
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
    void onProjectScreenClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-screen.fxml"));
        Parent root = loader.load();
        ProjectScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) projectScreen.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 750));
    }
    public void loadActiveTasks() throws IOException {
        vbox.getChildren().clear();
        List<Task> projectTasks = dataStore.getProjectTasks(project.getProjectId());
        for (Task task : projectTasks){
            if(task.getStatus().equals("In Progress")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/TaskScreen/task.fxml"));
                VBox vboxItem = loader.load();
                TaskController controller = loader.getController();
                controller.setCurrentProject(currentUser);
                controller.setData(task);
                controller.setProjectDetailController(this);
                vbox.getChildren().add(vboxItem);
            }
        }
    }
    public void loadDoneTask() throws IOException {
        vbox.getChildren().clear();
        List<Task> projectTasks = dataStore.getProjectTasks(project.getProjectId());
        for (Task task : projectTasks){
            if(task.getStatus().equals("Done")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/TaskScreen/task.fxml"));
                VBox vboxItem = loader.load();
                TaskController controller = loader.getController();
                controller.setCurrentProject(currentUser);
                controller.setData(task);
                controller.setProjectDetailController(this);
                vbox.getChildren().add(vboxItem);
            }
        }
    }
    public void onShowAllTaskButton(ActionEvent actionEvent) throws IOException {
        loadAllTasks();
    }

    public void onShowActiveTaskButton(ActionEvent actionEvent) throws IOException {
        loadActiveTasks();
    }
    public void onShowDoneTaskButton(ActionEvent actionEvent) throws IOException {
        loadDoneTask();
    }

    @FXML
    void onManageMembersButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/add-member-to-project.fxml"));
        Parent root = fxmlLoader.load();
        AddMemberController controller = fxmlLoader.getController();
        controller.setCurrentUser(currentUser);
        controller.setProject(project);
        controller.setProjectDetailController(this);
        
        // Kiểm tra xem user có phải leader không
        boolean isLeader = dataStore.isProjectLeader(currentUser.getUserId(), project.getProjectId());
        controller.setIsLeader(isLeader);
        
        Stage stage = new Stage();
        stage.setTitle(isLeader ? "Manage Members" : "View Members");
        stage.setScene(new Scene(root, 500, 450));
        stage.show();
    }

    public void refreshProjectDetail() throws IOException {
        this.dataStore = FileContact.loadDataStore();
        loadAllTasks();
    }
}
