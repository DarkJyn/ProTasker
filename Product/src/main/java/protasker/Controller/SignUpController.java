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
import javafx.stage.Stage;
import protasker.Model.Authenticator;

import java.io.IOException;

public class SignUpController{
    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label signUpNotiLabel;

    @FXML
    void onSignUpButton(ActionEvent event) throws IOException {
        String result = Authenticator.registerUser(usernameTextField.getText(),passwordTextField.getText(),confirmPasswordTextField.getText());
        signUpNotiLabel.setText(result);
        if(result.equals("Successfully registered!")){
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/login-screen.fxml"));
            stage.setScene(new Scene(root, 900, 600));
        }
    }
}
