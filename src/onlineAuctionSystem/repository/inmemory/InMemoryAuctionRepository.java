package onlineAuctionSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.repository.AuctionRepository;
import onlineAuctionSystem.utils.strategy.AuctionSearchStrategy;

public class InMemoryAuctionRepository implements AuctionRepository {
    private final Map<String, Auction> auctionList;
    private static InMemoryAuctionRepository INSTANCE;

    private InMemoryAuctionRepository() {
        auctionList = new ConcurrentHashMap<>();
    }

    public static synchronized InMemoryAuctionRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryAuctionRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Auction auction) {
        auctionList.put(auction.getAuctionId(), auction);
    }

    @Override
    public List<Auction> find(AuctionSearchStrategy searchStrategy) {
        return searchStrategy.search(List.copyOf(auctionList.values()));
    }

}
