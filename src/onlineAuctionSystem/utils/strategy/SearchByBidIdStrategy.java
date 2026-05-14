package onlineAuctionSystem.utils.strategy;

import java.util.List;

import onlineAuctionSystem.models.auction.Bid;

public class SearchByBidIdStrategy implements BidSearchStrategy {
    private final String bidId;

    public SearchByBidIdStrategy(String bidId) {
        this.bidId = bidId;
    }

    @Override
    public List<Bid> search(List<Bid> bids) {
        return bids.stream().filter(bid -> bid.getBidId().equals(bidId)).toList();
    }

}
