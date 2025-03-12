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
import javafx.scene.input.MouseEvent;
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
    void onSignUpScreen(MouseEvent event) throws IOException {
        Stage stage = (Stage) signUpScreen.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/signup-screen.fxml"));
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    void onLogInButtonClick(ActionEvent event) throws IOException {
        String result = Authenticator.checkLogin(usernameTextField.getText(), PasswordTextField.getText());
        loginNotiLabel.setText(result);
        if(result.equals("Login Successful")){
            User user = new User(usernameTextField.getText(), PasswordTextField.getText());
            Stage stage = (Stage) logInButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/dash-board.fxml"));
            Parent root = loader.load();
            DashBoardController dashBoardController = loader.getController();
            dashBoardController.setCurrentUser(user);
            stage.setScene(new Scene(root, 900, 600));
        }
    }
}