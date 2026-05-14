package onlineAuctionSystem.models.auction.auctionState;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.Bid;

public class CancelledAuctionState implements AuctionState {

    @Override
    public void placeBid(Auction auction, Bid bid, Bid currentHighestBid) {
        throw new UnsupportedOperationException("Cannot place bid. Auction cancelled");
    }

    @Override
    public void cancel(Auction auction) {
        System.out.println("Auction is already cancelled.");
    }

    @Override
    public void close(Auction auction) {
        throw new UnsupportedOperationException("Cannot close. Auction is cancelled.");
    }

}
