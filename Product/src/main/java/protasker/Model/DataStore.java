package protasker.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStore {
    private List<User> users;
    private List<Project> projects;
    private List<UserProject> userProjects;
    private List<Task> tasks;

    public DataStore() {
        this.users = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.userProjects = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User findUserById(String userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Project findProjectById(String projectId) {
        return projects.stream()
                .filter(p -> p.getProjectId().equals(projectId))
                .findFirst()
                .orElse(null);
    }

    public List<Project> getUserProjects(String userId) {
        return userProjects.stream()
                .filter(up -> up.getUserId().equals(userId))
                .map(up -> findProjectById(up.getProjectId()))
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    public List<User> getProjectMembers(String projectId) {
        return userProjects.stream()
                .filter(up -> up.getProjectId().equals(projectId))
                .map(up -> findUserById(up.getUserId()))
                .filter(u -> u != null)
                .collect(Collectors.toList());
    }

    public List<Task> getProjectTasks(String projectId) {
        return tasks.stream()
                .filter(t -> t.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    public List<Task> getUserTasks(String userId) {
        return tasks.stream()
                .filter(t -> t.getAssignedUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void addUserToProject(String userId, String projectId, String role) {
        userProjects.add(new UserProject(userId, projectId, role));
    }

    public void removeUserFromProject(String userId, String projectId) {
        userProjects.removeIf(up -> up.getUserId().equals(userId) && up.getProjectId().equals(projectId));
    }

    public boolean isUserInProject(String userId, String projectId) {
        return userProjects.stream()
                .anyMatch(up -> up.getUserId().equals(userId) && up.getProjectId().equals(projectId));
    }

    public String getUserRole(String userId, String projectId) {
        return userProjects.stream()
                .filter(up -> up.getUserId().equals(userId) && up.getProjectId().equals(projectId))
                .map(UserProject::getRole)
                .findFirst()
                .orElse(null);
    }

    public boolean isProjectLeader(String userId, String projectId) {
        String role = getUserRole(userId, projectId);
        return "owner".equals(role);
    }

    public List<User> getAllUsersExceptInProject(String projectId) {
        List<String> projectMemberIds = userProjects.stream()
                .filter(up -> up.getProjectId().equals(projectId))
                .map(UserProject::getUserId)
                .collect(Collectors.toList());
        
        return users.stream()
                .filter(u -> !projectMemberIds.contains(u.getUserId()))
                .collect(Collectors.toList());
    }

    // Xóa project cùng với tất cả tasks và user-project relationships liên quan
    public void deleteProject(String projectId) {
        // Xóa tất cả tasks của project
        tasks.removeIf(t -> t.getProjectId().equals(projectId));
        
        // Xóa tất cả user-project relationships
        userProjects.removeIf(up -> up.getProjectId().equals(projectId));
        
        // Xóa project
        projects.removeIf(p -> p.getProjectId().equals(projectId));
    }

    // Xóa task theo taskId
    public void deleteTask(String taskId) {
        tasks.removeIf(t -> t.getTaskId().equals(taskId));
    }
}
