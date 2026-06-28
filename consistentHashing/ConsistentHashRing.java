import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class ConsistentHashRing {
    private final int virtualNodes;
    private final TreeMap<Long, String> ring = new TreeMap<>();
    private final Set<String> servers = new HashSet<>();

    public ConsistentHashRing(Collection<String> initialServers, int virtualNodes) {
        if (virtualNodes <= 0) {
            throw new IllegalArgumentException("virtualNodes must be positive");
        }
        this.virtualNodes = virtualNodes;

        for (String server : initialServers) {
            addServer(server);
        }
    }

    private long hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return ByteBuffer.wrap(bytes).getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    public void addServer(String server) {
        if (!servers.add(server)) {
            return;
        }

        for (int i = 0; i < virtualNodes; i++) {
            ring.put(hash(server + "#" + i), server);
        }
    }

    public void removeServer(String server) {
        if (!servers.remove(server)) {
            return;
        }

        for (int i = 0; i < virtualNodes; i++) {
            ring.remove(hash(server + "#" + i));
        }
    }

    public String getServer(String key) {
        if (ring.isEmpty()) {
            throw new IllegalStateException("hash ring has no servers");
        }

        long position = hash(key);
        Map.Entry<Long, String> entry = ring.ceilingEntry(position);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }
}
