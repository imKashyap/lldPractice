import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SmoothedLatency {
    private final Map<String, Double> latencyMs = new HashMap<>();
    private final double alpha;

    SmoothedLatency(List<String> backends, double alpha) {
        for (String backend : backends)
            latencyMs.put(backend, null);
        this.alpha = alpha;
    }

    void record(String backend, double observedMs) {
        Double current = latencyMs.get(backend);
        latencyMs.put(
                backend,
                current == null ? observedMs : (1 - alpha) * current + alpha * observedMs);
    }

    String pick() {
        return latencyMs.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseGet(() -> latencyMs.keySet().iterator().next());
    }
}
