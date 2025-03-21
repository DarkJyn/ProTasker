package protasker.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Project {
    String name;
    String priority;
    String description;
    UserInfo leader;
    String StartDate;
    String TargetDate;
    ArrayList<Task> tasks = new ArrayList<>();
    String progress;
    Boolean searchValue = true;
    public Project(String name, String priority, String description, UserInfo leader, String startDate, String targetDate) {
        this.name = name;
        searchValue = true;
        this.priority = priority;
        this.description = description;
        this.leader = leader;
        StartDate = startDate;
        TargetDate = targetDate;
    }

    public LocalDate getDueDateAsLocalDate() {
        return LocalDate.parse(TargetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public int getProgressAsInt() {
        getProgress();
        return Integer.parseInt(progress.replace("%", ""));
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
    public UserInfo getLeader() {
        return leader;
    }
    public void setSearchValue(Boolean searchValue) {
        this.searchValue = searchValue;
    }
    public Boolean getSearchValue() {
        return searchValue;
    }
    public String getStartDate() {
        return StartDate;
    }
    public String getTargetDate() {
        return TargetDate;
    }
    public String getProgress() {
        progress = setProgress() + "%";
        return progress;
    }
    public String setProgress() {
        int doneTaskcnt = 0;
        int totalTaskcnt = tasks.size();
        for(Task task : tasks) {
            String status = task.getStatus();
            if(status.equals("Done")) doneTaskcnt++;
        }
        if(totalTaskcnt == 0) {return "0";}
        return Integer.toString( (int)(doneTaskcnt * 100 / totalTaskcnt)) ;
//        return "66%";
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }
}
