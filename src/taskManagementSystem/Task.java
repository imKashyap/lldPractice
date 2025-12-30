package taskManagementSystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Task {
    private String title;
    private String description;
    private final LocalDate createdAt;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;
    private User assignedUser;
    private final List<TaskHistoryEntry> taskHistory;

    Task(){
        this.createdAt = LocalDate.now();
        taskHistory = new CopyOnWriteArrayList<>();
    }
    public User getAssignedUser() {
        return assignedUser;
    }

    public synchronized void setAssignedUser(User assignedUser) {
        String action = assignedUser == null?"Task assigned to User: ": "Task reassigned to User: ";
        action +=  assignedUser.getName();
        this.assignedUser = assignedUser;
        notify(action);
    }

    public String getTitle() {
        return title;
    }

    public synchronized void setTitle(String title) {
        String action = title == null?"New title: ": "Task title renamed to: ";
        action+=title;
        this.title = title;
        notify(action);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public synchronized String getDescription() {
        return description;
    }

    public synchronized void setDescription(String description) {
         String action = title == null?"New description: ": "Task description renamed to: ";
         action+=description;
         this.description = description;
         notify(action);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public synchronized void setDueDate(LocalDate dueDate) {
       String action = title == null?"New Due Date: ": "Task Due Date updated to: ";
       action+= dueDate.format(DateTimeFormatter.ISO_DATE);
       this.dueDate = dueDate;
       notify(action);
    }

    public TaskStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(TaskStatus status) {
        String action = title == null?"Task Status: ": "Task moved to: ";
        action+=status.name();
        this.status = status;
        notify(action);
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public synchronized void setPriority(TaskPriority priority) {
        String action = priority == null?" Task Priority set: ":" Task Priority updated to: ";
        action+=priority.name();
        this.priority = priority;
        notify(action);
    }

    public List<TaskHistoryEntry> getTaskHistory(){
        return taskHistory;
    }

    public void showTaskHistory(){
        taskHistory.forEach(taskHistoryEntry->{
            System.out.printf("[%s] Task %s: %s%n", taskHistoryEntry.timestamp, title, taskHistoryEntry.action);
        });
    }

    public void notify(String action){
        new TaskUpdationObserver().update(this, action);
    }
}
