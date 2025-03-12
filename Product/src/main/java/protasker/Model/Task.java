package protasker.Model;

public class Task {
    String name;
    String description;
    String status;
    Project projectOwn;
    User userOwn;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Project getProjectOwn() {
        return projectOwn;
    }

    public User getUserOwn() {
        return userOwn;
    }

    public void setUserOwn(User userOwn) {
        this.userOwn = userOwn;
    }

    public void setProjectOwn(Project projectOwn) {
        this.projectOwn = projectOwn;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
