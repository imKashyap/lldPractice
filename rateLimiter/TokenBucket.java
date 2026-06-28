package rateLimiter;

import java.util.HashMap;
import java.util.Map;

class TokenBucketLimiter {
    private final double capacity;
    private final double refillRatePerSecond;
    private final Map<String, Bucket> buckets;

    private static class Bucket {
        double tokens;
        double lastRefillTime;

        Bucket(double tokens, double lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }

    public TokenBucketLimiter(double capacity, double refillRatePerSecond) {
        if (capacity <= 0 || refillRatePerSecond <= 0) {
            throw new IllegalArgumentException("capacity and refill rate must be positive");
        }

        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.buckets = new HashMap<>();
    }

    public boolean allow(String key) {
        return allow(key, 1.0, null);
    }

    public boolean allow(String key, double cost, Double now) {
        if (cost <= 0) {
            throw new IllegalArgumentException("cost must be positive");
        }

        double current = now == null ? currentTimeSeconds() : now;
        Bucket bucket = buckets.getOrDefault(key, new Bucket(capacity, current));

        double elapsed = Math.max(0, current - bucket.lastRefillTime);
        double tokens = Math.min(capacity, bucket.tokens + elapsed * refillRatePerSecond);

        if (tokens < cost) {
            buckets.put(key, new Bucket(tokens, current));
            return false;
        }

        tokens -= cost;
        buckets.put(key, new Bucket(tokens, current));
        return true;
    }

    private double currentTimeSeconds() {
        return System.nanoTime() / 1_000_000_000.0;
    }
}
