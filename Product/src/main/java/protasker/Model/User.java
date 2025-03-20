package protasker.Model;

import java.util.ArrayList;
public class User {
    String username;
    String password;
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<Task> tasksList = new ArrayList<>();
    String userAvatarPath = "D:\\Dean'sCode\\PROPTIT\\OOP-Java\\ProTasker\\Product\\src\\main\\resources\\ImageDashBoard\\avatar2.jpg";
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
    public UserInfo toUserInfo(){
        return new UserInfo(username, userAvatarPath);
    }
    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTasksList(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
    }
    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public void setUserAvatarPath(String userAvatarPath) {this.userAvatarPath = userAvatarPath;}
    public String getUserAvatarPath() {
        return userAvatarPath;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
}
