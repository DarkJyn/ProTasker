package protasker.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private String taskId;
    private String name;
    private String description;
    private String status;
    private String projectId;
    private String assignedUserId;
    private String priority;
    transient StringProperty statusProperty = new SimpleStringProperty();
    
    public Task(String name, String description, String status, String projectId, String assignedUserId, String priority) {
        this.taskId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.status = status;
        this.statusProperty.set(status);
        this.projectId = projectId;
        this.assignedUserId = assignedUserId;
        this.priority = priority;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
        statusProperty = new SimpleStringProperty(status);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
