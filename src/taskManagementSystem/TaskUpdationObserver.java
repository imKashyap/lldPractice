package taskManagementSystem;

public class TaskUpdationObserver implements Observer{
    public void update(Task task, String action){
        TaskHistoryEntry historyEntry = new TaskHistoryEntry(action);
        task.getTaskHistory().add(historyEntry);
    }
}
