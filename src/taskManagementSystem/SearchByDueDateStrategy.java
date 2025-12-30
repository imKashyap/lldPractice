package taskManagementSystem;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchByDueDateStrategy implements SearchStrategy{
    private final  LocalDate dueDate;

    public SearchByDueDateStrategy(LocalDate date) {
        dueDate = date;
    }

    @Override
    public Set<Task> search(Set<Task> tasks) {
        return tasks.stream().filter(task -> task.getDueDate().equals(dueDate)).collect(Collectors.toSet());
    }
}
