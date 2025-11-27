package protasker.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Project {
    private String projectId;
    private String name;
    private String priority;
    private String description;
    private String leaderId;
    private String startDate;
    private String targetDate;
    private Boolean searchValue = true;
    
    public Project(String name, String priority, String description, String leaderId, String startDate, String targetDate) {
        this.projectId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.searchValue = true;
        this.priority = priority;
        this.description = description;
        this.leaderId = leaderId;
        this.startDate = startDate;
        this.targetDate = targetDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public LocalDate getDueDateAsLocalDate() {
        return LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public int getProgressAsInt(ArrayList<Task> projectTasks) {
        return Integer.parseInt(calculateProgress(projectTasks).replace("%", ""));
    }
    public int getPriorityAsInt() {
        return switch (priority) {
            case "Low" -> 3;
            case "Medium" -> 2;
            case "High" -> 1;
            default -> 0;
        };
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSearchValue(Boolean searchValue) {
        this.searchValue = searchValue;
    }
    
    public Boolean getSearchValue() {
        return searchValue;
    }
    
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String calculateProgress(ArrayList<Task> projectTasks) {
        int doneTaskcnt = 0;
        int totalTaskcnt = projectTasks.size();
        for(Task task : projectTasks) {
            String status = task.getStatus();
            if(status.equals("Done")) doneTaskcnt++;
        }
        if(totalTaskcnt == 0) {return "0%";}
        return Integer.toString((int)(doneTaskcnt * 100 / totalTaskcnt)) + "%";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
