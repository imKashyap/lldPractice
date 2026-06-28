import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class RoundRobin {
    private final List<String> backends;
    private final AtomicInteger index = new AtomicInteger(0);

    RoundRobin(List<String> backends) {
        if (backends.isEmpty())
            throw new IllegalArgumentException("backends cannot be empty");
        this.backends = backends;
    }

    String pick() {
        int i = Math.floorMod(index.getAndIncrement(), backends.size());
        return backends.get(i);
    }
}
