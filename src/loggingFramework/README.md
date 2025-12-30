# Logging Framework

### Requirements:
1. The logging framework should support different log levels, such as DEBUG, INFO, WARNING, ERROR, and FATAL.
2. It should allow logging messages with a timestamp, log level, and message content.
3. The framework should support multiple output destinations, such as console, file, and database.
4. It should provide a configuration mechanism to set the log level and output destination.
5. The logging framework should be thread-safe to handle concurrent logging from multiple threads.
6. It should be extensible to accommodate new log levels and output destinations in the future.
----

### ✅ Key Components in the Design

**1. Entities / Classes**

| Entity/Class Name       | Type             | Description                                                                 |
|-------------------------|------------------|-----------------------------------------------------------------------------|
| `LogLevel`              | `Enum`           | Defines supported log levels (`DEBUG`, `INFO`, `WARNING`, `ERROR`, `FATAL`). |
| `LogMessage`            | `Class`          | Encapsulates the log message with timestamp, level, and message content.    |
| `LogHandler`            | `Interface`      | Strategy interface to define how logs should be handled/output.             |
| `ConsoleLogHandler`     | `Class`          | Outputs logs to the console. Implements `LogHandler`.                       |
| `FileLogHandler`        | `Class`          | Outputs logs to a file. Thread-safe via `synchronized`. Implements `LogHandler`. |
| `DatabaseLogHandler`    | `Class`          | Mocks writing logs to a database. Implements `LogHandler`.                  |
| `LoggerConfig`          | `Singleton Class`| Holds configuration such as log level and output handlers. Uses builder-like configuration chaining. |
| `Logger`                | `Singleton Class`| Central class to log messages. Filters based on log level and dispatches to all configured handlers. |


**2. Design Patterns Used**

| Pattern Name        | Used In                                               | Purpose / Description                                                                 |
|---------------------|--------------------------------------------------------|----------------------------------------------------------------------------------------|
| **Strategy**        | `LogHandler` and its implementations                  | Allows plugging in different output strategies dynamically.                            |
| **Singleton**       | `Logger`, `LoggerConfig`                              | Ensures only one instance exists globally and provides global access.                  |
| **Builder (Fluent Interface)** | `LoggerConfig`                              | Allows easy and readable chaining for setting log level and adding handlers.           |
| **Open-Closed Principle (OCP)** | `LogHandler`, `LogLevel`                  | Open for extension (new handlers/levels) but closed for modification.                  |
| **Factory (optional)** | (Potential extension for handler creation)         | Helps decouple object creation from usage — not implemented yet, but good for configs. |


**3. Concurrency Handling**
* Logger is a singleton and stateless, making it inherently thread-safe.
* FileLogHandler uses synchronized to ensure file write atomicity.
* Further optimization can use a producer-consumer queue (ExecutorService) for async logging.