package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import protasker.Model.DataStore;
import protasker.Model.Project;
import protasker.Model.Task;
import protasker.Model.User;
import protasker.Model.FileContact;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    User leader;
    private ProjectScreenController projectScreenController;
    private ContextMenu contextMenu;
    
    public static String formatDate(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date.format(outputFormatter);
    }
    void setCurrentUser(User user) {
        leader = user;
    }
    
    void setProjectScreenController(ProjectScreenController controller) {
        this.projectScreenController = controller;
    }
    
    void setProject(Project project) {
        System.out.println(project == null);
        this.project = project;
        DataStore dataStore = FileContact.loadDataStore();
        ArrayList<Task> tasks = (ArrayList<Task>) dataStore.getProjectTasks(project.getProjectId());
        int progress = this.project.getProgressAsInt(tasks);
        progressLabel.setText(progress + "%");
        System.out.println("set project hbox done");
    }

    void setData(Project project) {
        projectNameLabel.setText(project.getName());
        priorityLabel.setText(project.getPriority());
        targetDateLabel.setText(formatDate(project.getTargetDate()));
        
        DataStore dataStore = FileContact.loadDataStore();
        ArrayList<Task> tasks = (ArrayList<Task>) dataStore.getProjectTasks(project.getProjectId());
        int progress = project.getProgressAsInt(tasks);
        progressLabel.setText(progress + "%");
        
        User projectLeader = dataStore.getUsers().stream()
            .filter(u -> u.getUserId().equals(project.getLeaderId()))
            .findFirst().orElse(null);
        
        if(projectLeader != null) {
            if(projectLeader.getUserAvatarPath() != null && !projectLeader.getUserAvatarPath().isEmpty()) {
                try {
                    File file = new File(projectLeader.getUserAvatarPath());
                    Image image = new Image(file.toURI().toString());
                    avatarImg.setImage(image);
                } catch (Exception e) {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                    avatarImg.setImage(defaultImage);
                }
            } else {
                Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                avatarImg.setImage(defaultImage);
            }
        }
    }

    @FXML
    void onProjectHboxClick(MouseEvent event) throws IOException {
        // Nếu là click phải, hiển thị context menu
        if (event.getButton() == MouseButton.SECONDARY) {
            showContextMenu(event);
            return;
        }
        
        // Click trái - mở project detail
        if (event.getButton() == MouseButton.PRIMARY) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-detail.fxml"));
            Parent root = loader.load();
            ProjectDetailController controller = loader.getController();
            controller.setCurrentUser(leader);
            controller.setProject(project);
            Stage stage = (Stage) projectHbox.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 750));
        }
    }
    
    private void showContextMenu(MouseEvent event) {
        // Kiểm tra xem user có phải leader không
        DataStore dataStore = FileContact.loadDataStore();
        boolean isLeader = dataStore.isProjectLeader(leader.getUserId(), project.getProjectId());
        
        if (!isLeader) {
            return; // Không phải leader thì không hiện menu
        }
        
        // Tạo context menu
        if (contextMenu != null) {
            contextMenu.hide();
        }
        
        contextMenu = new ContextMenu();
        
        // Tạo menu item Delete
        MenuItem deleteItem = new MenuItem("Delete Project");
        deleteItem.setStyle("-fx-text-fill: #e74c3c;");
        
        deleteItem.setOnAction(e -> {
            try {
                deleteProject();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        contextMenu.getItems().add(deleteItem);
        contextMenu.show(projectHbox, event.getScreenX(), event.getScreenY());
    }
    
    private void deleteProject() throws IOException {
        // Hiển thị dialog xác nhận
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Project");
        confirmAlert.setHeaderText("Are you sure you want to delete this project?");
        confirmAlert.setContentText("Project: " + project.getName() + "\n\nThis will also delete all tasks in this project. This action cannot be undone.");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            // Xóa project
            DataStore dataStore = FileContact.loadDataStore();
            dataStore.deleteProject(project.getProjectId());
            FileContact.saveDataStore(dataStore);
            
            // Refresh project screen
            if (projectScreenController != null) {
                projectScreenController.refreshProjects();
            }
            
            // Thông báo thành công
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Project deleted successfully!");
            successAlert.showAndWait();
        }
    }
}
