package onlineAuctionSystem.models.auction;

import java.time.LocalDateTime;

public class Bid {
    private final String bidId;
    private final String auctionId;
    private final double amount;
    private final String bidder;
    private final LocalDateTime createdAt;

    public Bid(String bidId, String auctionId, String bidder, double amount) {
        this.bidId = bidId;
        this.auctionId = auctionId;
        this.amount = amount;
        this.bidder = bidder;
        this.createdAt = LocalDateTime.now();
    }

    public String getBidId() {
        return bidId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getBidder() {
        return bidder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Bid [bidId=" + bidId + ", auctionId=" + auctionId + ", amount=" + amount + ", bidder=" + bidder
                + ", createdAt=" + createdAt + "]";
    }

}
