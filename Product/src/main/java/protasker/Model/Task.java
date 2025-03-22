package protasker.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    String name;
    String description;
    String status;
    String projectOwn;
    UserInfo userOwn;
    String priority;
    transient StringProperty statusProperty = new SimpleStringProperty();
    public Task(String name, String description, String status, String projectOwn, UserInfo userOwn,String priority) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.statusProperty.set(status);
        this.projectOwn = projectOwn;
        this.priority = priority;
        this.userOwn = userOwn;
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
//        statusProperty.set(this.status);
    }
    public String getProjectOwn() {
        return projectOwn;
    }

    public UserInfo getUserOwn() {
        return userOwn;
    }

    public void setProjectOwn(String projectOwn) {
        this.projectOwn = projectOwn;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
