package rateLimiter;

import java.util.HashMap;
import java.util.Map;

class SlidingWindowCounterLimiter {
    private final int limit;
    private final int windowSeconds;
    private final Map<String, State> state;

    private static class State {
        double currentStart;
        int previousCount;
        int currentCount;

        State(double currentStart, int previousCount, int currentCount) {
            this.currentStart = currentStart;
            this.previousCount = previousCount;
            this.currentCount = currentCount;
        }
    }

    public SlidingWindowCounterLimiter(int limit, int windowSeconds) {
        if (limit <= 0 || windowSeconds <= 0) {
            throw new IllegalArgumentException("limit and window size must be positive");
        }

        this.limit = limit;
        this.windowSeconds = windowSeconds;
        this.state = new HashMap<>();
    }

    public boolean allow(String key) {
        return allow(key, currentTimeSeconds());
    }

    public boolean allow(String key, double now) {
        double windowStart = now - (now % windowSeconds);

        State windowState = state.getOrDefault(key, new State(windowStart, 0, 0));

        if (windowStart > windowState.currentStart) {
            int windowsPassed = (int) ((windowStart - windowState.currentStart) / windowSeconds);
            windowState.previousCount = windowsPassed == 1 ? windowState.currentCount : 0;
            windowState.currentCount = 0;
            windowState.currentStart = windowStart;
        }

        double elapsed = now - windowState.currentStart;
        double previousWeight = ((double) windowSeconds - elapsed) / windowSeconds;
        double estimatedCount = windowState.previousCount * previousWeight + windowState.currentCount;

        if (estimatedCount + 1 > limit) {
            state.put(key, windowState);
            return false;
        }

        windowState.currentCount += 1;
        state.put(key, windowState);
        return true;
    }

    private double currentTimeSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
