package protasker.Model;

public class UserProject {
    private String userId;
    private String projectId;
    private String role;

    public UserProject(String userId, String projectId, String role) {
        this.userId = userId;
        this.projectId = projectId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
