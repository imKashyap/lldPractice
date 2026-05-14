package onlineAuctionSystem.utils.strategy;

import java.util.List;

import onlineAuctionSystem.models.auction.Auction;

public class SearchByNameStrategy implements AuctionSearchStrategy {
    private final String itemName;

    public SearchByNameStrategy(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public List<Auction> search(List<Auction> auctions) {
        return auctions.stream().filter(auction -> {
            return auction.getItem().getName().equals(itemName);
        }).toList();
    }

}
