package loggingFramework;

public class Logger {
    private static final Logger INSTANCE = new Logger();
    private final LoggerConfig config = LoggerConfig.getInstance();

    private Logger() {}

    public static Logger getInstance() {
        return INSTANCE;
    }

    public void log(LogLevel level, String message) {
        if (level.isEnabledFor(config.getCurrentLevel())) {
            LogMessage logMessage = new LogMessage(level, message);
            for (LogHandler handler : config.getHandlers()) {
                handler.handle(logMessage);
            }
        }
    }

    // Convenience methods
    public void debug(String msg) { log(LogLevel.DEBUG, msg); }
    public void info(String msg) { log(LogLevel.INFO, msg); }
    public void warn(String msg) { log(LogLevel.WARNING, msg); }
    public void error(String msg) { log(LogLevel.ERROR, msg); }
    public void fatal(String msg) { log(LogLevel.FATAL, msg); }
}
