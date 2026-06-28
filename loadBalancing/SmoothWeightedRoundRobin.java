import java.util.ArrayList;
import java.util.List;

class SmoothWeightedRoundRobin {
    private static class Backend {
        String name;
        int weight;
        int current;

        Backend(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
    }

    private final List<Backend> backends = new ArrayList<>();
    private final int totalWeight;

    SmoothWeightedRoundRobin(List<String> names, List<Integer> weights) {
        if (names.size() != weights.size() || names.isEmpty()) {
            throw new IllegalArgumentException("names and weights must be non-empty and equal length");
        }
        int total = 0;
        for (int i = 0; i < names.size(); i++) {
            backends.add(new Backend(names.get(i), weights.get(i)));
            total += weights.get(i);
        }
        totalWeight = total;
    }

    String pick() {
        Backend best = null;
        for (Backend backend : backends) {
            backend.current += backend.weight;
            if (best == null || backend.current > best.current) {
                best = backend;
            }
        }
        best.current -= totalWeight;
        return best.name;
    }
}
