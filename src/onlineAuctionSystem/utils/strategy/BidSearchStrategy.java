package onlineAuctionSystem.utils.strategy;

import java.util.List;

import onlineAuctionSystem.models.auction.Bid;

public interface BidSearchStrategy {
    List<Bid> search(List<Bid> bids);
}
