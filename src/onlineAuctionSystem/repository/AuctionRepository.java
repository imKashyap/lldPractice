package onlineAuctionSystem.repository;

import java.util.List;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.utils.strategy.AuctionSearchStrategy;

public interface AuctionRepository {
    void save(Auction auction);

    List<Auction> find(AuctionSearchStrategy searchStrategy);

}
