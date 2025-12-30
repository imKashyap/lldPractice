package loggingFramework;

public class LoggerDemo {
    public static void main(String[] args) {
        try {
            LoggerConfig config = LoggerConfig.getInstance()
                    .setLogLevel(LogLevel.DEBUG)
                    .addHandler(new ConsoleLogHandler());
//                    .addHandler(new FileLogHandler("app.log"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Logger logger = Logger.getInstance();
        logger.debug("This is a debug message");
        logger.error("This is an error message");
    }
}
