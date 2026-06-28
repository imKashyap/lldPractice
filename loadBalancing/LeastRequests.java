import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class LeastRequests {
    private final Map<String, Integer> active = new HashMap<>();
    private final Random random = new Random();

    LeastRequests(List<String> backends) {
        for (String backend : backends)
            active.put(backend, 0);
    }

    synchronized String pick() {
        int min = active.values().stream().min(Integer::compareTo).orElseThrow();
        List<String> candidates = active.entrySet().stream()
                .filter(entry -> entry.getValue() == min)
                .map(Map.Entry::getKey)
                .toList();
        String chosen = candidates.get(random.nextInt(candidates.size()));
        active.put(chosen, active.get(chosen) + 1);
        return chosen;
    }

    synchronized void release(String backend) {
        active.computeIfPresent(backend, (key, count) -> Math.max(0, count - 1));
    }
}
