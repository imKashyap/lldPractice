package rateLimiter;

import java.util.HashMap;
import java.util.Map;

class FixedWindowCounter {
    private final int limit;
    private final int windowSeconds;
    private final Map<String, WindowState> counters;

    private static class WindowState {
        long window;
        int count;

        WindowState(long window) {
            this.window = window;
            this.count = 0;
        }
    }

    FixedWindowCounter(int limit, int windowSeconds) {
        if (limit <= 0 || windowSeconds <= 0) {
            throw new IllegalArgumentException("limit and window size must be positive");
        }

        this.limit = limit;
        this.windowSeconds = windowSeconds;
        this.counters = new HashMap<>();
    }

    boolean allow(String key) {
        return allow(key, null);
    }

    boolean allow(String key, Double now) {
        double current = now == null ? currentTimeSeconds() : now;
        long window = (long) (current / windowSeconds);
        WindowState state = counters.computeIfAbsent(key, ignored -> new WindowState(window));

        if (state.window != window) {
            state.window = window;
            state.count = 0;
        }

        if (state.count >= limit) {
            return false;
        }

        state.count += 1;
        return true;
    }

    private double currentTimeSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
