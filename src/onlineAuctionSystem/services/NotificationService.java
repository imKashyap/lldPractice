package onlineAuctionSystem.services;

import java.util.List;

import onlineAuctionSystem.models.auction.AuctionEvent;
import onlineAuctionSystem.models.auction.BidPlacedEvent;
import onlineAuctionSystem.models.user.User;
import onlineAuctionSystem.repository.UserRepository;
import onlineAuctionSystem.utils.AuctionEventListener;
import onlineAuctionSystem.utils.AuctionSubscriptionRegistry;

public class NotificationService implements AuctionEventListener {
    private final AuctionSubscriptionRegistry subscriptionRegistry;
    private final UserRepository userRepository;

    public NotificationService(
            AuctionSubscriptionRegistry subscriptionRegistry,
            UserRepository userRepository) {
        this.subscriptionRegistry = subscriptionRegistry;
        this.userRepository = userRepository;
    }

    @Override
    public void onEvent(AuctionEvent event) {
        if (event instanceof BidPlacedEvent bidPlacedEvent) {
            onBidPlaced(bidPlacedEvent);
        }
    }

    private void onBidPlaced(BidPlacedEvent event) {
        List<String> subscriberIds = subscriptionRegistry.getSubscribers(event.getAuctionId());

        for (String userId : subscriberIds) {
            // Optional: don't notify the bidder about their own bid
            if (userId.equals(event.getBidderId())) {
                continue;
            }

            userRepository.findByUsername(userId)
                    .map(User::getName)
                    .ifPresent(name -> System.out.println(
                            "Hey " + name
                                    + ", there is a new bid of "
                                    + event.getBidAmount()
                                    + " on auction "
                                    + event.getAuctionId()));
        }
    }
}
