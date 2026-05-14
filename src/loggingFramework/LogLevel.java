package loggingFramework;

public enum LogLevel {
    DEBUG(1),
    INFO(2),
    WARNING(3),
    ERROR(4),
    FATAL(5);

    private final int severity;

    LogLevel(int severity) {
        this.severity = severity;
    }

    public boolean isEnabledFor(LogLevel configuredLevel) {
        return this.severity >= configuredLevel.severity;
    }
}
