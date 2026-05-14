package onlineAuctionSystem.models.auction.auctionState;

import java.time.LocalDateTime;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.Bid;

public class ActiveAuctionState implements AuctionState {

    @Override
    public void placeBid(Auction auction, Bid bid, Bid currentHighestBid) {
        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            close(auction);
            throw new IllegalStateException("Cannot place bid. Auction has ended.");
        }
        if (auction.getSellerId().equals(bid.getBidder())) {
            throw new IllegalArgumentException("Seller cannot bid on their own auction.");
        }

        double minimumAmount = currentHighestBid == null
                ? auction.getStartingPrice()
                : currentHighestBid.getAmount();
        if (bid.getAmount() <= minimumAmount) {
            throw new IllegalArgumentException("Bid amount must be greater than " + minimumAmount);
        }

        System.out.println("Bid placed: " + bid);
    }

    @Override
    public void cancel(Auction auction) {
        auction.setState(new CancelledAuctionState());
        auction.setStatus(AuctionStatus.CANCELLED);
        System.out.println("Auction cancelled for: " + auction.getAuctionId());
    }

    @Override
    public void close(Auction auction) {
        auction.setState(new ClosedAuctionState());
        auction.setStatus(AuctionStatus.CLOSED);
        System.out.println("Auction closed for: " + auction.getAuctionId());
    }

}
