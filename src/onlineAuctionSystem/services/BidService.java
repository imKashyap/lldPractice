package onlineAuctionSystem.services;

import java.util.List;
import java.util.Optional;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.Bid;
import onlineAuctionSystem.models.auction.BidPlacedEvent;
import onlineAuctionSystem.repository.AuctionEventPublisher;
import onlineAuctionSystem.repository.AuctionRepository;
import onlineAuctionSystem.repository.BidRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryAuctionRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryBidRepository;
import onlineAuctionSystem.utils.AuctionSubscriptionRegistry;
import onlineAuctionSystem.utils.strategy.SearchByAuctionIdStrategy;
import onlineAuctionSystem.utils.strategy.SearchByBidIdStrategy;

public class BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionEventPublisher eventPublisher;
    private final AuctionSubscriptionRegistry auctionSubscriptionRegistry;

    public BidService() {
        bidRepository = InMemoryBidRepository.getInstance();
        auctionRepository = InMemoryAuctionRepository.getInstance();
        eventPublisher = new AuctionEventPublisher();
        auctionSubscriptionRegistry = AuctionSubscriptionRegistry.getInstance();
    }

    public BidService(
            BidRepository bidRepository,
            AuctionRepository auctionRepository,
            AuctionEventPublisher eventPublisher,
            AuctionSubscriptionRegistry auctionSubscriptionRegistry) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
        this.eventPublisher = eventPublisher;
        this.auctionSubscriptionRegistry = auctionSubscriptionRegistry;
    }

    public void placeBid(String bidId, String auctionId, String bidderUsername, double amount) {
        SearchByAuctionIdStrategy searchStrategy = new SearchByAuctionIdStrategy(auctionId);
        List<Auction> auctionFound = auctionRepository.find(searchStrategy);
        if (auctionFound.isEmpty()) {
            throw new IllegalArgumentException("Auction Id does not exist");
        }
        Auction auction = auctionFound.get(0);
        BidPlacedEvent event;

        synchronized (auction) {
            Bid bid = new Bid(bidId, auctionId, bidderUsername, amount);
            Optional<Bid> currentHighestBid = findCurrentHighestBid(auction);
            String previousHighestBidderId = currentHighestBid.map(Bid::getBidder).orElse(null);

            auction.getState().placeBid(auction, bid, currentHighestBid.orElse(null));
            bidRepository.save(bid);
            auction.setCurrentHighestBidId(bidId);

            event = new BidPlacedEvent(
                    auctionId,
                    bid.getBidder(),
                    bid.getAmount(),
                    previousHighestBidderId);
        }

        eventPublisher.publish(event);
        auctionSubscriptionRegistry.subscribe(auctionId, bidderUsername);
    }

    private Optional<Bid> findCurrentHighestBid(Auction auction) {
        String currentHighestBidId = auction.getCurrentHighestBidId();
        if (currentHighestBidId == null) {
            return Optional.empty();
        }
        return bidRepository.find(new SearchByBidIdStrategy(currentHighestBidId)).stream().findFirst();
    }

}
