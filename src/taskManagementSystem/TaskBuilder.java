package taskManagementSystem;

import java.time.LocalDate;

public class TaskBuilder {
    private final Task task = new Task();

    public TaskBuilder setTitle(String title){
        task.setTitle(title);
        return this;
    }

    public TaskBuilder setDescription(String description){
        task.setDescription(description);
        return this;
    }

    public TaskBuilder setDueDate(LocalDate date){
        task.setDueDate(date);
        return this;
    }

    public TaskBuilder setPriority(TaskPriority priority){
        task.setPriority(priority);
        return this;
    }

    public TaskBuilder setStatus(TaskStatus status){
        task.setStatus(status);
        return this;
    }

    public TaskBuilder setAssignedUser(User user){
        task.setAssignedUser(user);
        return this;
    }

    public Task build(){
        return task;
    }
}
