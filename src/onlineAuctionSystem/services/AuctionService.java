package onlineAuctionSystem.services;

import java.util.List;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.ItemCategory;
import onlineAuctionSystem.repository.AuctionRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryAuctionRepository;
import onlineAuctionSystem.utils.AuctionSubscriptionRegistry;
import onlineAuctionSystem.utils.strategy.AuctionSearchStrategy;
import onlineAuctionSystem.utils.strategy.SearchByAuctionIdStrategy;
import onlineAuctionSystem.utils.strategy.SearchByCategoryStrategy;
import onlineAuctionSystem.utils.strategy.SearchByNameStrategy;

public class AuctionService {
    private final AuctionSubscriptionRegistry auctionSubscriptionRegistry;
    private final AuctionRepository auctionRepository;

    public AuctionService() {
        this.auctionRepository = InMemoryAuctionRepository.getInstance();
        this.auctionSubscriptionRegistry = AuctionSubscriptionRegistry.getInstance();
    }

    public AuctionService(AuctionRepository auctionRepository, AuctionSubscriptionRegistry auctionSubscriptionRegistry) {
        this.auctionRepository = auctionRepository;
        this.auctionSubscriptionRegistry = auctionSubscriptionRegistry;
    }

    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
        auctionSubscriptionRegistry.subscribe(auction.getAuctionId(), auction.getSellerId());
    }

    public Auction findAuctionById(String auctionId) {
        List<Auction> auctions = auctionRepository.find(new SearchByAuctionIdStrategy(auctionId));
        if (auctions.isEmpty()) {
            throw new IllegalArgumentException("Auction Id does not exist");
        }
        return auctions.get(0);
    }

    public void closeAuction(String auctionId) {
        Auction auction = findAuctionById(auctionId);
        synchronized (auction) {
            auction.getState().close(auction);
        }
    }

    public List<Auction> findAuctionItemByName(String itemName) {
        AuctionSearchStrategy searchByName = new SearchByNameStrategy(itemName);
        return auctionRepository.find(searchByName);
    }

    public List<Auction> findAuctionItemByCategory(ItemCategory category) {
        AuctionSearchStrategy searchByName = new SearchByCategoryStrategy(category);
        return auctionRepository.find(searchByName);
    }
}
