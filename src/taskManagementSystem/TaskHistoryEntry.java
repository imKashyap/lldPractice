package taskManagementSystem;

import java.time.LocalDateTime;

class TaskHistoryEntry {
    LocalDateTime timestamp;
    String action;

    TaskHistoryEntry(String action){
        this.action=action;
        timestamp = LocalDateTime.now();
    }
}