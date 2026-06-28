import java.nio.charset.StandardCharsets;
import java.util.List;

class RendezvousHash {
    private final List<String> backends;

    RendezvousHash(List<String> backends) {
        if (backends.isEmpty())
            throw new IllegalArgumentException("backends cannot be empty");
        this.backends = backends;
    }

    String pick(String key) {
        String best = null;
        long bestScore = -1;
        for (String backend : backends) {
            long score = fnv1a32(key + "|" + backend);
            if (best == null || Long.compareUnsigned(score, bestScore) > 0) {
                best = backend;
                bestScore = score;
            }
        }
        return best;
    }

    private static long fnv1a32(String value) {
        long hash = 0x811c9dc5L;
        for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
            hash ^= Byte.toUnsignedInt(b);
            hash = (hash * 0x01000193L) & 0xffffffffL;
        }
        return hash;
    }
}
