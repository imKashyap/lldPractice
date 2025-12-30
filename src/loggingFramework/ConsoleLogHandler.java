package loggingFramework;

public class ConsoleLogHandler implements LogHandler{
    @Override
    public void handle(LogMessage message) {
        System.out.println(message.format());
    }
}
