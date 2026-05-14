package onlineAuctionSystem.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class AuctionSubscriptionRegistry {

    private static final AuctionSubscriptionRegistry INSTANCE = new AuctionSubscriptionRegistry();
    private final Map<String, Set<String>> auctionSubscribers = new HashMap<>();

    private AuctionSubscriptionRegistry() {
    }

    public static AuctionSubscriptionRegistry getInstance() {
        return INSTANCE;
    }

    public synchronized void subscribe(String auctionId, String userId) {
        auctionSubscribers
                .computeIfAbsent(auctionId, id -> new HashSet<>())
                .add(userId);
    }

    public synchronized List<String> getSubscribers(String auctionId) {
        return List.copyOf(auctionSubscribers.getOrDefault(auctionId, Set.of()));
    }
}
