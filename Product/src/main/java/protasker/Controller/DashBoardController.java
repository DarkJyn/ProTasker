package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import protasker.Model.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DashBoardController {
    private User currentUser;
    private DataStore dataStore;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView userAvatar;
    List<Project> searchProjects = new ArrayList<>();
    public void setCurrentUser(User currentUser) throws IOException {
        this.currentUser = currentUser;
        this.dataStore = FileContact.loadDataStore();
        searchProjects = dataStore.getUserProjects(currentUser.getUserId());
        usernameLabel.setText("Hi, " + currentUser.getUsername());
        
        // Load avatar
        if(currentUser.getUserAvatarPath() != null && !currentUser.getUserAvatarPath().isEmpty()) {
            try {
                File file = new File(currentUser.getUserAvatarPath());
                Image image = new Image(file.toURI().toString());
                userAvatar.setImage(image);
            } catch (Exception e) {
                Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                userAvatar.setImage(defaultImage);
            }
        } else {
            Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
            userAvatar.setImage(defaultImage);
        }
        loadProjects();
        searchField.setEditable(false);
        allProjectLabel.setStyle("-fx-text-fill: #3498db;-fx-cursor: hand");
        progressCircle = new ProgressCircle(90, 90);
        progressPane.getChildren().add(progressCircle);
        progressCircle.setProgress(0.5);
    }
    @FXML
    private VBox vbox;
    public void loadProjects() throws IOException {
        vbox.getChildren().clear();
        for (Project project : searchProjects) {
            if(project.getSearchValue()){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/ProjectScreen/project-dashboarditem.fxml"));
                VBox vboxItem = loader.load();
                System.out.println("test");
                ProjectItemController controller = loader.getController();
                controller.setCurrentUser(currentUser);
                controller.setData(project);
                controller.setDashBoardController(this);
                vbox.getChildren().add(vboxItem);
            }
        }
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
    private Label profileLabelInDashBoard;
    @FXML
    void onProfileInDashBoardClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/profile-screen.fxml"));
        Parent root = loader.load();
        ProfileScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) profileLabelInDashBoard.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 750));
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
        stage.setScene(new Scene(root, 1100, 750));
    }
    @FXML
    private Label allProjectLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private Label targetDate;
    @FXML
    void onAllProjectLabelClick(MouseEvent event) throws IOException {
        searchProjects.sort(Comparator.comparing(Project::getName));
        loadProjects();
        allProjectLabel.setStyle("-fx-text-fill: #3498db;-fx-cursor: hand;");
        priorityLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        progressLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        targetDate.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
    }
    @FXML
    void onPriorityLabelClick(MouseEvent event) throws IOException {
        searchProjects.sort(Comparator.comparingInt(Project::getPriorityAsInt));
        loadProjects();
        priorityLabel.setStyle("-fx-text-fill: #3498db;-fx-cursor: hand;");
        allProjectLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        progressLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        targetDate.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
    }
    @FXML
    void onProgressLabelClick(MouseEvent event) throws IOException {
        searchProjects.sort(Comparator.comparingInt(p -> p.getProgressAsInt((ArrayList<Task>)dataStore.getProjectTasks(p.getProjectId()))));
        loadProjects();
        allProjectLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        priorityLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        targetDate.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        progressLabel.setStyle("-fx-text-fill: #3498db;-fx-cursor: hand;");
    }
    @FXML
    void onTargetDateClick(MouseEvent event) throws IOException {
        searchProjects.sort(Comparator.comparing(Project::getDueDateAsLocalDate));
        loadProjects();
        allProjectLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        priorityLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        progressLabel.setStyle("-fx-text-fill: black;-fx-cursor: hand;");
        targetDate.setStyle("-fx-text-fill: #3498db;-fx-cursor: hand;");
    }
    @FXML
    private TextField searchField;
    public void onSearchClick(MouseEvent mouseEvent) {
        searchField.setEditable(true);
        searchField.requestFocus();
    }
    public void handleEnterKey(ActionEvent actionEvent) throws IOException {
        String text = searchField.getText().toLowerCase();
        System.out.println(text);
        if(text != null){
            for(Project project : searchProjects){
                if(project.getName().toLowerCase().contains(text)){
                    project.setSearchValue(true);
                }
                else project.setSearchValue(false);
            }
        }
        else {
            for(Project project : searchProjects){
                project.setSearchValue(true);
            }
        }
        loadProjects();
    }
    @FXML
    private Label projectNameRightSide;
    void setProjectNameRightSide(String name) {
        projectNameRightSide.setText(name);
    }
    @FXML
    private Label runningTaskRightSide;
    void setRunningTaskRightSide(String number) {
        runningTaskRightSide.setText(number);
    }
    @FXML
    private Label totalTaskRightSide;
    void setTotalTaskRightSide(String name) {
        totalTaskRightSide.setText(name);
    }
    @FXML
    private Pane progressPane;
    private ProgressCircle progressCircle;
    public void updateProgress(int value) {
        double progressValue = Math.max(0, Math.min(100, value)) / 100.0;
        progressCircle.setProgress(progressValue);
    }

    public void onContactButtonClick(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/dwng.vu.zxje/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
