package taskManagementSystem;

import java.util.Set;

public interface SearchStrategy {
    Set<Task> search(Set<Task> tasks);
}
