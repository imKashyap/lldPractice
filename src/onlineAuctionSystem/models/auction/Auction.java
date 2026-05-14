package onlineAuctionSystem.models.auction;

import java.time.LocalDateTime;

import onlineAuctionSystem.models.auction.auctionState.ActiveAuctionState;
import onlineAuctionSystem.models.auction.auctionState.AuctionState;
import onlineAuctionSystem.models.auction.auctionState.AuctionStatus;

public class Auction {
    private final String auctionId;
    private final Item item;
    private final double startingPrice;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private volatile AuctionStatus status;
    private volatile AuctionState state;
    private final String sellerId;
    private volatile String currentHighestBidId;
    private Bid winningBid;

    public Auction(String auctionId, Item item, double startingPrice, LocalDateTime endTime, String sellerId) {
        this.auctionId = auctionId;
        this.item = item;
        this.startingPrice = startingPrice;
        this.endTime = endTime;
        this.sellerId = sellerId;
        this.startTime = LocalDateTime.now();
        this.status = AuctionStatus.ACTIVE;
        this.state = new ActiveAuctionState();
    }

    public Item getItem() {
        return item;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public AuctionState getState() {
        return state;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getCurrentHighestBidId() {
        return currentHighestBidId;
    }

    public Bid getWinningBid() {
        return winningBid;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public void setState(AuctionState state) {
        this.state = state;
    }

    public void setCurrentHighestBidId(String currentHighestBidId) {
        this.currentHighestBidId = currentHighestBidId;
    }

    public synchronized void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
    }

}
