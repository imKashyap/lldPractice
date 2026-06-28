package rateLimiter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class LeakyBucketLimiter {
    private final int capacity;
    private final double leakIntervalSeconds;
    private final Map<String, Deque<Double>> queues;

    public LeakyBucketLimiter(int capacity, double leakRatePerSecond) {
        if (capacity <= 0 || leakRatePerSecond <= 0) {
            throw new IllegalArgumentException("capacity and leak rate must be positive");
        }

        this.capacity = capacity;
        this.leakIntervalSeconds = 1.0 / leakRatePerSecond;
        this.queues = new HashMap<>();
    }

    public boolean allow(String key) {
        return allow(key, null);
    }

    public boolean allow(String key, Double now) {
        double current = now == null ? currentTimeSeconds() : now;
        Deque<Double> queue = queues.computeIfAbsent(key, k -> new ArrayDeque<>());

        while (!queue.isEmpty() && queue.peekFirst() <= current) {
            queue.pollFirst();
        }

        if (queue.size() >= capacity) {
            return false;
        }

        double lastScheduled = queue.isEmpty() ? current : queue.peekLast();
        queue.addLast(Math.max(current, lastScheduled) + leakIntervalSeconds);
        return true;
    }

    private double currentTimeSeconds() {
        return System.nanoTime() / 1_000_000_000.0;
    }
}
