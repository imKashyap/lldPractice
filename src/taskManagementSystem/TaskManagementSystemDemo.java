package taskManagementSystem;

import java.time.LocalDate;

public class TaskManagementSystemDemo {
    public static void main(String[] args) {
        User user1 = new User("US-1234", "Rahul");
        User user2 = new User("US-2345", "Omprakash");
        Manager manager = Manager.getInstance();

        Task task =new TaskBuilder()
                .setTitle("Buy Milk")
                .setDescription("Buy 1l milk by evening")
                .setPriority(TaskPriority.MEDIUM)
                .build();

        manager.createTask(task);
        user1.assignTask(task, user2);
        task.setDueDate(LocalDate.now().plusDays(1));


    }
}
