package protasker.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Project {
    String name;
    String priority;
    String description;
    User Leader;
    String StartDate;
    String TargetDate;
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<User> members;
    String progress;

    public String getPriority() {
        return priority;
    }
    public LocalDate getDueDateAsLocalDate() {
        return LocalDate.parse(TargetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public int getProgressAsInt() {
        return Integer.parseInt(progress.replace("%", ""));
    }
    public int getPriorityAsInt() {
        return switch (priority) {
            case "Low" -> 1;
            case "Medium" -> 2;
            case "High" -> 3;
            default -> 0;
        };
    }
    public String getName() {
        return name;
    }

    public User getLeader() {
        return Leader;
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

    public Project(String name, String priority, String description, User leader, String startDate, String targetDate) {
        this.name = name;
        this.priority = priority;
        this.description = description;
        Leader = leader;
        StartDate = startDate;
        TargetDate = targetDate;
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
}
