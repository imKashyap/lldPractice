package onlineAuctionSystem.models.auction.auctionState;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.Bid;

public interface AuctionState {
    void placeBid(Auction auction, Bid bid, Bid currentHighestBid);

    void cancel(Auction auction);

    void close(Auction auction);
}
