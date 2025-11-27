package protasker.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import protasker.Model.Authenticator;
import protasker.Model.User;

import java.io.IOException;
public class LoginController{

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Button logInButton;

    @FXML
    private Label signUpScreen;

    @FXML
    private Label loginNotiLabel;

    @FXML
    private TextField usernameTextField;
    @FXML
    void initialize() {
        assert PasswordTextField != null : "fx:id=\"PasswordTextField\" was not injected: check your FXML file 'login-screen.fxml'.";
        assert logInButton != null : "fx:id=\"logInButton\" was not injected: check your FXML file 'login-screen.fxml'.";
        assert loginNotiLabel != null : "fx:id=\"loginNotiLabel\" was not injected: check your FXML file 'login-screen.fxml'.";
        assert signUpScreen != null : "fx:id=\"signUpScreen\" was not injected: check your FXML file 'login-screen.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'login-screen.fxml'.";
        logInButton.setTextFill(Color.WHITE);
    }
    @FXML
    void onSignUpScreen(MouseEvent event) throws IOException {
        Stage stage = (Stage) signUpScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInAndSignUp/signup-screen.fxml"));
        stage.setScene(new Scene(root, 1100, 750));
    }
    @FXML
    void onLogInButtonClick(ActionEvent event) throws IOException {
        User user = Authenticator.checkLogin(usernameTextField.getText(), PasswordTextField.getText());
        if(user != null){
            Stage stage = (Stage) logInButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DashBoard/dash-board.fxml"));
            Parent root = loader.load();
            DashBoardController dashBoardController = loader.getController();
            dashBoardController.setCurrentUser(user);
            stage.setScene(new Scene(root, 1100, 750));
        }
        else{
            loginNotiLabel.setText("Invalid Username or Password");
        }
    }
}