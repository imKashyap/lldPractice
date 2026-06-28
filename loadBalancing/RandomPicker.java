import java.util.List;
import java.util.Random;

public class RandomPicker {
    private final List<String> backends;
    private final Random random = new Random();

    RandomPicker(List<String> backends) {
        if (backends.isEmpty())
            throw new IllegalArgumentException("backends cannot be empty");
        this.backends = backends;
    }

    String pick() {
        return backends.get(random.nextInt(backends.size()));
    }
}
