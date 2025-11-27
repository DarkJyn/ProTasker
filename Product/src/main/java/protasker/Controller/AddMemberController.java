package protasker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import protasker.Model.DataStore;
import protasker.Model.FileContact;
import protasker.Model.Project;
import protasker.Model.User;

import java.io.IOException;
import java.util.List;

public class AddMemberController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private ListView<String> memberListView;

    private Project project;
    private User currentUser;
    private DataStore dataStore;
    private ProjectDetailController projectDetailController;
    private boolean isLeader = false;

    public void setProject(Project project) throws IOException {
        this.project = project;
        this.dataStore = FileContact.loadDataStore();
        loadAvailableUsers();
        loadCurrentMembers();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setProjectDetailController(ProjectDetailController controller) {
        this.projectDetailController = controller;
    }

    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
        // Ẩn/hiện các control dựa trên role
        addButton.setVisible(isLeader);
        addButton.setManaged(isLeader);
        userComboBox.setVisible(isLeader);
        userComboBox.setManaged(isLeader);
    }

    private void loadAvailableUsers() {
        List<User> availableUsers = dataStore.getAllUsersExceptInProject(project.getProjectId());
        ObservableList<User> observableList = FXCollections.observableArrayList(availableUsers);
        userComboBox.setItems(observableList);

        // Custom cell factory để hiển thị username
        userComboBox.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        setText((user == null || empty) ? null : user.getUsername());
                    }
                };
            }
        });

        // Custom button cell
        userComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText((user == null || empty) ? null : user.getUsername());
            }
        });
    }

    private void loadCurrentMembers() {
        List<User> members = dataStore.getProjectMembers(project.getProjectId());
        ObservableList<String> memberNames = FXCollections.observableArrayList();

        for (User user : members) {
            String role = dataStore.getUserRole(user.getUserId(), project.getProjectId());
            String displayText = user.getUsername() + " (" + (role.equals("owner") ? "Owner" : "Member") + ")";
            memberNames.add(displayText);
        }

        memberListView.setItems(memberNames);

        // Custom cell factory để thêm nút Remove cho members (trừ owner)
        memberListView.setCellFactory(param -> new ListCell<String>() {
            private final Button removeButton = new Button("Remove");
            private final javafx.scene.layout.HBox container = new javafx.scene.layout.HBox();
            private final javafx.scene.control.Label nameLabel = new javafx.scene.control.Label();

            {
                removeButton.setStyle("-fx-cursor: hand; -fx-text-fill: white; -fx-background-color: #e74c3c; -fx-background-radius: 5; -fx-font-size: 12px;");
                removeButton.setOnAction(event -> {
                    String selectedMember = getItem();
                    if (selectedMember != null && !selectedMember.contains("(Owner)")) {
                        String username = selectedMember.split(" \\(")[0];
                        User userToRemove = dataStore.findUserByUsername(username);
                        if (userToRemove != null) {
                            try {
                                removeMember(userToRemove);
                            } catch (IOException e) {
                                showAlert("Error", "Failed to remove member", Alert.AlertType.ERROR);
                            }
                        }
                    }
                });
                
                container.setSpacing(10);
                container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                javafx.scene.layout.HBox.setHgrow(nameLabel, javafx.scene.layout.Priority.ALWAYS);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(null);
                    nameLabel.setText(item);
                    container.getChildren().clear();
                    container.getChildren().add(nameLabel);
                    
                    // Chễ hiển thị nút Remove cho leader và không phải owner
                    if (isLeader && !item.contains("(Owner)")) {
                        container.getChildren().add(removeButton);
                    }
                    setGraphic(container);
                }
            }
        });
    }

    @FXML
    void onAddButtonClick(ActionEvent event) throws IOException {
        User selectedUser = userComboBox.getValue();
        if (selectedUser == null) {
            showAlert("Error", "Please select a user", Alert.AlertType.ERROR);
            return;
        }

        // Thêm user vào project với role "member"
        dataStore.addUserToProject(selectedUser.getUserId(), project.getProjectId(), "member");
        FileContact.saveDataStore(dataStore);

        // Refresh lists
        loadAvailableUsers();
        loadCurrentMembers();

        // Clear selection
        userComboBox.setValue(null);

        showAlert("Success", "Member added successfully", Alert.AlertType.INFORMATION);
    }

    private void removeMember(User user) throws IOException {
        // Confirm dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Removal");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to remove " + user.getUsername() + " from the project?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            dataStore.removeUserFromProject(user.getUserId(), project.getProjectId());
            FileContact.saveDataStore(dataStore);

            // Refresh lists
            loadAvailableUsers();
            loadCurrentMembers();

            showAlert("Success", "Member removed successfully", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    void onCloseButtonClick(ActionEvent event) throws IOException {
        // Refresh ProjectDetailController nếu có thay đổi
        if (projectDetailController != null) {
            projectDetailController.refreshProjectDetail();
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
