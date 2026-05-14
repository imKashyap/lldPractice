package taskManagementSystem;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Manager {

    private static Manager INSTANCE = new Manager();
    private Set<Task> taskList = ConcurrentHashMap.newKeySet();

    private Manager() {
    }

    public static Manager getInstance() {
        return INSTANCE;
    }

    public void createTask(Task task) {
        taskList.add(task);
    }

    public void deleteTask(Task task) {
        taskList.remove(task);
    }

    public Set<Task> searchBy(User user) {
        return new SearchByUserStrategy(user).search(taskList);
    }

    public Set<Task> searchBy(User user, TaskPriority priority) {
        return new SearchByPriorityStrategy(priority).search(new SearchByUserStrategy(user).search(taskList));
    }

    public Set<Task> searchBy(User user, LocalDate dueDate) {
        return new SearchByDueDateStrategy(dueDate).search(new SearchByUserStrategy(user).search(taskList));
    }

}
