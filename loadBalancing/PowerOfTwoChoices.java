import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class PowerOfTwoChoices {
    private final Map<String, Integer> active = new HashMap<>();
    private final Random random = new Random();

    PowerOfTwoChoices(List<String> backends) {
        if (backends.size() < 2)
            throw new IllegalArgumentException("need at least two backends");
        for (String backend : backends)
            active.put(backend, 0);
    }

    synchronized String pick() {
        var names = active.keySet().stream().toList();
        String a = names.get(random.nextInt(names.size()));
        String b;
        do {
            b = names.get(random.nextInt(names.size()));
        } while (a.equals(b));

        String chosen = active.get(a) <= active.get(b) ? a : b;
        active.put(chosen, active.get(chosen) + 1);
        return chosen;
    }
}
