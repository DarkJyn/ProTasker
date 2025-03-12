package protasker.Model;

import java.util.ArrayList;

public class Project {
    String name;
    String priority;
    String description;
    User Leader;
    String StartDate;
    String TargetDate;
    ArrayList<Task> tasks;
    ArrayList<User> members;
    int progress;

    public String getPriority() {
        return priority;
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

    public int getProgress() {
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
    public int setProgress(int progress) {
        int doneTaskcnt = 0;
        int totalTaskcnt = tasks.size();
        for(Task task : tasks) {
            String status = task.getStatus();
            if(status.equals("Done")){
                doneTaskcnt++;
            }
        }
        if(totalTaskcnt == 0) {return 0;}
        return (int) doneTaskcnt * 100 / totalTaskcnt;
    }
}
