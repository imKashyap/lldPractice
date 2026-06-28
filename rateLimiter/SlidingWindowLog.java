package rateLimiter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class SlidingWindowLogLimiter {
    private final int limit;
    private final long windowSeconds;
    private final Map<String, Deque<Double>> events;

    public SlidingWindowLogLimiter(int limit, long windowSeconds) {
        if (limit <= 0 || windowSeconds <= 0) {
            throw new IllegalArgumentException("limit and window size must be positive");
        }

        this.limit = limit;
        this.windowSeconds = windowSeconds;
        this.events = new HashMap<>();
    }

    public boolean allow(String key) {
        return allow(key, null);
    }

    public boolean allow(String key, Double now) {
        double current = now == null ? currentTimeSeconds() : now;
        double cutoff = current - windowSeconds;
        Deque<Double> timestamps = events.computeIfAbsent(key, k -> new ArrayDeque<>());

        while (!timestamps.isEmpty() && timestamps.peekFirst() <= cutoff) {
            timestamps.pollFirst();
        }

        if (timestamps.size() >= limit) {
            return false;
        }

        timestamps.addLast(current);
        return true;
    }

    private double currentTimeSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
