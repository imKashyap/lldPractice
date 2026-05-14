package loggingFramework;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoggerConfig {
    private static LoggerConfig instance;
    private volatile LogLevel currentLevel;
    private final List<LogHandler> handlers;

    private LoggerConfig() {
        this.currentLevel = LogLevel.INFO;
        this.handlers = new CopyOnWriteArrayList<>();
    }

    public static synchronized LoggerConfig getInstance() {
        if (instance == null) {
            instance = new LoggerConfig();
        }
        return instance;
    }

    public LoggerConfig setLogLevel(LogLevel level) {
        this.currentLevel = Objects.requireNonNull(level, "level cannot be null");
        return this;
    }

    public LoggerConfig addHandler(LogHandler handler) {
        this.handlers.add(Objects.requireNonNull(handler, "handler cannot be null"));
        return this;
    }

    public LogLevel getCurrentLevel() { return currentLevel; }
    public List<LogHandler> getHandlers() { return List.copyOf(handlers); }
}
