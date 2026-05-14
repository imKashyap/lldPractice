package onlineAuctionSystem.utils.strategy;

import java.util.List;

import onlineAuctionSystem.models.auction.Auction;

public interface AuctionSearchStrategy {
    List<Auction> search(List<Auction> auctions);
}
