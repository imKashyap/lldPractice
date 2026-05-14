package onlineAuctionSystem.repository;

import java.util.List;

import onlineAuctionSystem.models.auction.Bid;
import onlineAuctionSystem.utils.strategy.BidSearchStrategy;

public interface BidRepository {
    void save(Bid bid);

    List<Bid> find(BidSearchStrategy searchStrategy);

}
