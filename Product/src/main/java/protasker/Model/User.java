package protasker.Model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class User {
    String username;
    String password;
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<Task> tasksList = new ArrayList<>();
    Image userAvatar = new Image(getClass().getResourceAsStream("/ImageDashBoard/avatar.jpg"));

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        projects = new ArrayList<>();
        System.out.println("creat project list in user class");
        tasksList = new ArrayList<>();
    }
    public ArrayList<Project> getProjects() {
        return projects;
    }

    public Image getUserAvatarPath() {
        return userAvatar;
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }
    public String getUsername() {
        return username;
    }
}
