package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import protasker.Model.DataStore;
import protasker.Model.User;
import protasker.Model.FileContact;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProfileScreenController {

    @FXML
    private Label overviewLabelInDashBoard;
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private Label taskCompletedLabel;
    
    private User currentUser;
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        
        // Hiển thị tên user
        userNameLabel.setText(currentUser.getUsername());
        
        // Tính % task hoàn thành
        DataStore dataStore = FileContact.loadDataStore();
        List<protasker.Model.Task> userTasks = dataStore.getUserTasks(currentUser.getUserId());
        int totalTasks = userTasks.size();
        int completedTasks = 0;
        
        for(protasker.Model.Task task : userTasks) {
            if(task.getStatus().equals("Done")) {
                completedTasks++;
            }
        }
        
        double percentage = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0;
        taskCompletedLabel.setText(String.format("%.1f%%", percentage));
        
        // Load avatar
        if(currentUser.getUserAvatarPath() != null && !currentUser.getUserAvatarPath().isEmpty()) {
            try {
                File file = new File(currentUser.getUserAvatarPath());
                Image image = new Image(file.toURI().toString());
                avatarUser.setImage(image);
            } catch (Exception e) {
                Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
                avatarUser.setImage(defaultImage);
            }
        } else {
            Image defaultImage = new Image(getClass().getResourceAsStream("/View/avt_defaul.jpg"));
            avatarUser.setImage(defaultImage);
        }
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
    private Label projectScreen;

    @FXML
    private Label taskScreen;

    @FXML
    private Label logOut;
    @FXML
    void onLogOutClick(MouseEvent event) throws IOException {
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInAndSignUp/login-screen.fxml"));
        stage.setScene(new Scene(root, 1100, 750));
    }
    @FXML
    void onProjectClick(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProjectScreen/project-screen.fxml"));
        Parent root = loader.load();
        ProjectScreenController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Stage stage = (Stage) projectScreen.getScene().getWindow();
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
    private ImageView avatarUser;
    @FXML
    void initialize() {
        assert avatarUser != null : "fx:id=\"avatarUser\" was not injected: check your FXML file 'project-screen.fxml'.";
        Rectangle rect = new Rectangle(200,200);
        avatarUser.setClip(rect);
    }

    public void onUploadNewImageButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagePath = file.getAbsolutePath();
            currentUser.setUserAvatarPath(imagePath);
            System.out.println(imagePath);
            Image image = new Image(file.toURI().toString());
            avatarUser.setImage(image);
            DataStore dataStore = FileContact.loadDataStore();
            FileContact.updateUser(dataStore, currentUser);
        }
    }
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Button onConfirmButton;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private TextField usernameTextField;
    @FXML
    void onConfirmButtonClick(ActionEvent event) throws IOException {
        DataStore dataStore = FileContact.loadDataStore();
        User user = dataStore.getUsers().stream()
            .filter(u -> u.getUserId().equals(currentUser.getUserId()))
            .findFirst().orElse(null);
        
        if(user != null) {
            if(usernameTextField.getText() != null && !usernameTextField.getText().isEmpty() 
               && oldPasswordField.getText().equals(user.getPassword()) 
               && newPasswordField.getText() != null && !newPasswordField.getText().isEmpty()){
                currentUser.setUsername(usernameTextField.getText());
                currentUser.setPassword(newPasswordField.getText());
                user.setUsername(currentUser.getUsername());
                user.setPassword(currentUser.getPassword());
                FileContact.updateUser(dataStore, user);
                System.out.println("Change User info done");
            }
        }
        newPasswordField.clear();
        oldPasswordField.clear();
        usernameTextField.clear();
        Stage stage = (Stage) logOut.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInAndSignUp/login-screen.fxml"));
        stage.setScene(new Scene(root, 1100, 750));
    }
}
