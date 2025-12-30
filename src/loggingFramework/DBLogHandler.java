package loggingFramework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DBLogHandler implements LogHandler{

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DBLogHandler(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(LogMessage message) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO logs (level, message, timestamp) VALUES (?, ?, ?)")) {
            statement.setString(1, message.getLevel().toString());
            statement.setString(2, message.getMessage());
            statement.setString(3, message.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

