package onlineAuctionSystem.models.auction;

public class BidPlacedEvent implements AuctionEvent {
    private final String auctionId;
    private final String bidderId;
    private final double bidAmount;
    private final String previousHighestBidderId;

    public BidPlacedEvent(
            String auctionId,
            String bidderId,
            double bidAmount,
            String previousHighestBidderId) {
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.bidAmount = bidAmount;
        this.previousHighestBidderId = previousHighestBidderId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public String getPreviousHighestBidderId() {
        return previousHighestBidderId;
    }

}
