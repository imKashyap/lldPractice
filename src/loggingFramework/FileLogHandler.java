package loggingFramework;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogHandler implements LogHandler {
    private final BufferedWriter writer;

    public FileLogHandler(String filePath) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath, true));
    }

    public synchronized void handle(LogMessage message) {
        try {
            writer.write(message.format());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
