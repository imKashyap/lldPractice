package onlineAuctionSystem.utils.strategy;

import java.util.List;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.ItemCategory;

public class SearchByCategoryStrategy implements AuctionSearchStrategy {
    private final ItemCategory category;

    public SearchByCategoryStrategy(ItemCategory category) {
        this.category = category;
    }

    @Override
    public List<Auction> search(List<Auction> auctions) {
        return auctions.stream()
                .filter(auction -> auction.getItem().getCategory() == category).toList();
    }

}
