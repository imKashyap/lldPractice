package onlineAuctionSystem.models.auction.auctionState;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.Bid;

public class ClosedAuctionState implements AuctionState {

    @Override
    public void placeBid(Auction auction, Bid bid, Bid currentHighestBid) {
        throw new UnsupportedOperationException("Cannot place bid. Auction closed already.");
    }

    @Override
    public void cancel(Auction auction) {
        throw new UnsupportedOperationException("Cannot cancel. Auction is closed.");
    }

    @Override
    public void close(Auction auction) {
        System.out.println("Auction is already closed.");
    }

}
