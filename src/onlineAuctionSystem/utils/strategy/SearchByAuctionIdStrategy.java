package onlineAuctionSystem.utils.strategy;

import java.util.List;

import onlineAuctionSystem.models.auction.Auction;

public class SearchByAuctionIdStrategy implements AuctionSearchStrategy {
    private final String auctionId;

    public SearchByAuctionIdStrategy(String auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public List<Auction> search(List<Auction> auctions) {
        return auctions.stream().filter(auction -> auction.getAuctionId().equals(auctionId)).toList();
    }

}
