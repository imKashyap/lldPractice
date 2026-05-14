package onlineAuctionSystem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import onlineAuctionSystem.models.auction.Auction;
import onlineAuctionSystem.models.auction.Item;
import onlineAuctionSystem.models.auction.ItemCategory;
import onlineAuctionSystem.models.user.Account;
import onlineAuctionSystem.models.user.User;
import onlineAuctionSystem.repository.AuctionEventPublisher;
import onlineAuctionSystem.repository.AuctionRepository;
import onlineAuctionSystem.repository.BidRepository;
import onlineAuctionSystem.repository.UserRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryAuctionRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryBidRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryUserRepository;
import onlineAuctionSystem.services.AuctionService;
import onlineAuctionSystem.services.BidService;
import onlineAuctionSystem.services.NotificationService;
import onlineAuctionSystem.services.UserService;
import onlineAuctionSystem.utils.AuctionSubscriptionRegistry;

public class OnlineAuctionSystemDemo {
    public static void main(String[] args) throws InterruptedException {
        UserRepository userRepository = new InMemoryUserRepository();
        AuctionRepository auctionRepository = InMemoryAuctionRepository.getInstance();
        BidRepository bidRepository = InMemoryBidRepository.getInstance();
        AuctionSubscriptionRegistry subscriptionRegistry = AuctionSubscriptionRegistry.getInstance();
        AuctionEventPublisher eventPublisher = new AuctionEventPublisher();

        UserService userService = new UserService(userRepository);
        AuctionService auctionService = new AuctionService(auctionRepository, subscriptionRegistry);
        BidService bidService = new BidService(
                bidRepository,
                auctionRepository,
                eventPublisher,
                subscriptionRegistry);

        NotificationService notificationService = new NotificationService(subscriptionRegistry, userRepository);
        eventPublisher.registerListener(notificationService);

        userService.registerUser(new User(new Account("seller", "seller-pass"), "Kashyap"));
        userService.registerUser(new User(new Account("alice", "alice-pass"), "Alice"));
        userService.registerUser(new User(new Account("bob", "bob-pass"), "Bob"));
        userService.registerUser(new User(new Account("charlie", "charlie-pass"), "Charlie"));

        Item painting = new Item(
                "ITEM-1",
                "Landscape Painting",
                "Original framed landscape artwork",
                ItemCategory.ARTWORK);
        Auction auction = new Auction(
                "AUC-1",
                painting,
                1000.0,
                LocalDateTime.now().plusMinutes(10),
                "seller");

        auctionService.createAuction(auction);
        subscriptionRegistry.subscribe("AUC-1", "alice");
        subscriptionRegistry.subscribe("AUC-1", "bob");
        subscriptionRegistry.subscribe("AUC-1", "charlie");

        System.out.println("=== Search Auctions ===");
        List<Auction> artworkAuctions = auctionService.findAuctionItemByCategory(ItemCategory.ARTWORK);
        System.out.println("Artwork auctions found: " + artworkAuctions.size());
        System.out.println("Search by item name: " + auctionService.findAuctionItemByName("Landscape Painting").size());

        System.out.println("\n=== Sequential Bidding With Notifications ===");
        bidService.placeBid("BID-1", "AUC-1", "alice", 1200.0);
        bidService.placeBid("BID-2", "AUC-1", "bob", 1500.0);

        System.out.println("\n=== Invalid Bid ===");
        try {
            bidService.placeBid("BID-3", "AUC-1", "charlie", 1400.0);
        } catch (RuntimeException exception) {
            System.out.println("Rejected bid: " + exception.getMessage());
        }

        System.out.println("\n=== Concurrent Bidding ===");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> placeBidSafely(bidService, "BID-4", "AUC-1", "alice", 1600.0));
        executorService.submit(() -> placeBidSafely(bidService, "BID-5", "AUC-1", "bob", 1700.0));
        executorService.submit(() -> placeBidSafely(bidService, "BID-6", "AUC-1", "charlie", 1800.0));
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        Auction updatedAuction = auctionService.findAuctionById("AUC-1");
        System.out.println("Current highest bid id: " + updatedAuction.getCurrentHighestBidId());

        System.out.println("\n=== Close Auction ===");
        auctionService.closeAuction("AUC-1");
        System.out.println("Auction status: " + updatedAuction.getStatus());

        System.out.println("\n=== Bid After Close ===");
        try {
            bidService.placeBid("BID-7", "AUC-1", "alice", 2000.0);
        } catch (RuntimeException exception) {
            System.out.println("Rejected bid: " + exception.getMessage());
        }
    }

    private static void placeBidSafely(
            BidService bidService,
            String bidId,
            String auctionId,
            String bidder,
            double amount) {
        try {
            bidService.placeBid(bidId, auctionId, bidder, amount);
            System.out.println("Accepted concurrent bid: " + bidId + " amount=" + amount);
        } catch (RuntimeException exception) {
            System.out.println("Rejected concurrent bid " + bidId + ": " + exception.getMessage());
        }
    }
}
