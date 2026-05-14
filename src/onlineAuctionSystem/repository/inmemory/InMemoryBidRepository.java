package onlineAuctionSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import onlineAuctionSystem.models.auction.Bid;
import onlineAuctionSystem.repository.BidRepository;
import onlineAuctionSystem.utils.strategy.BidSearchStrategy;

public class InMemoryBidRepository implements BidRepository {
    private final Map<String, Bid> bidList;
    private static InMemoryBidRepository INSTANCE;

    private InMemoryBidRepository() {
        bidList = new ConcurrentHashMap<>();
    }

    public static synchronized InMemoryBidRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryBidRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Bid bid) {
        bidList.put(bid.getBidId(), bid);
    }

    @Override
    public List<Bid> find(BidSearchStrategy searchStrategy) {
        return searchStrategy.search(List.copyOf(bidList.values()));
    }

}
