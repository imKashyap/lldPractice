package loggingFramework;

import java.time.LocalDateTime;

public class LogMessage {
    private final LogLevel level;
    private final String message;
    private final LocalDateTime timestamp;

    public LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String format() {
        return String.format("[%s] [%s]: %s", timestamp, level, message);
    }

    public LogLevel getLevel() { return level; }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
