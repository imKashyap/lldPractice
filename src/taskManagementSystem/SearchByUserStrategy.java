package taskManagementSystem;

import java.util.Set;
import java.util.stream.Collectors;

public class SearchByUserStrategy implements SearchStrategy{
    private final User user;
    SearchByUserStrategy(User user){
        this.user = user;
    }

    @Override
    public Set<Task> search(Set<Task> tasks) {
        return tasks.stream().filter(task->
             task.getAssignedUser().equals(user)
        ).collect(Collectors.toSet());
    }
}
