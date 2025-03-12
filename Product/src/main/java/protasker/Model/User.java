package protasker.Model;

import java.util.ArrayList;

public class User {
    String username;
    String password;
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<Task> tasksList = new ArrayList<>();
    String userAvatarPath;
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
    public String getUsername() {
        return username;
    }
}
