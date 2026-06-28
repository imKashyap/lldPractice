import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsistentHashRing ring = new ConsistentHashRing(
                List.of("S0", "S1", "S2", "S3", "S4"),
                100);

        System.out.println("user:123 -> " + ring.getServer("user:123"));
        System.out.println("order:987 -> " + ring.getServer("order:987"));

        ring.addServer("S5");
        System.out.println("After adding S5:");
        System.out.println("user:123 -> " + ring.getServer("user:123"));

        ring.removeServer("S2");
        System.out.println("After removing S2:");
        System.out.println("order:987 -> " + ring.getServer("order:987"));
    }
}
