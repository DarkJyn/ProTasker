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
import protasker.Model.FileContact;
import protasker.Model.User;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProfileScreenController {

    @FXML
    private Label overviewLabelInDashBoard;
    private User currentUser;
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        File file = new File(currentUser.getUserAvatarPath());
        Image image = new Image(file.toURI().toString());
        avatarUser.setImage(image);
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
            Image image = new Image(file.toURI().toString()); // Chuyển path thành URI
            avatarUser.setImage(image);
            FileContact.saveUsersToJson(currentUser);
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
    void onConfirmButtonClick(ActionEvent event) {
        List<User> usersList = FileContact.loadUserFromJson();
        for (User user : usersList){
            if (user.getUsername().equals(currentUser.getUsername())){
                if(usernameTextField.getText() != null && oldPasswordField.getText().equals(user.getPassword()) && newPasswordField.getText() != null ){
                    currentUser.setUsername(usernameTextField.getText());
                    currentUser.setPassword(newPasswordField.getText());
                    System.out.println("Change User info done");
                }
//                user.setUserAvatarPath(currentUser.getUserAvatarPath());
                user.setUsername(currentUser.getUsername());
                user.setPassword(currentUser.getPassword());
//                user.setProjects(currentUser.getProjects());
//                user.setTasksList(currentUser.getTasksList());
                break;
            }
        }
        FileContact.writeUsersToJson(usersList);
        newPasswordField.clear();
        oldPasswordField.clear();
        usernameTextField.clear();
    }
}
