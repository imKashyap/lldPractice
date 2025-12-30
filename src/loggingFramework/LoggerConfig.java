package loggingFramework;

import java.util.ArrayList;
import java.util.List;

public class LoggerConfig {
    private static LoggerConfig instance;
    private LogLevel currentLevel;
    private List<LogHandler> handlers;

    private LoggerConfig() {
        this.currentLevel = LogLevel.INFO;
        this.handlers = new ArrayList<>();
    }

    public static synchronized LoggerConfig getInstance() {
        if (instance == null) {
            instance = new LoggerConfig();
        }
        return instance;
    }

    public LoggerConfig setLogLevel(LogLevel level) {
        this.currentLevel = level;
        return this;
    }

    public LoggerConfig addHandler(LogHandler handler) {
        this.handlers.add(handler);
        return this;
    }

    public LogLevel getCurrentLevel() { return currentLevel; }
    public List<LogHandler> getHandlers() { return handlers; }
}

