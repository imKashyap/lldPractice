package taskManagementSystem;

import java.util.Set;
import java.util.stream.Collectors;

public class SearchByPriorityStrategy implements SearchStrategy {
    private final TaskPriority priority;

    public SearchByPriorityStrategy(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public Set<Task> search(Set<Task> tasks) {
        return tasks.stream().filter(task -> task.getPriority() == priority).collect(Collectors.toSet());
    }
}
