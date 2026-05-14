package onlineAuctionSystem.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import onlineAuctionSystem.models.auction.AuctionEvent;
import onlineAuctionSystem.utils.AuctionEventListener;

public class AuctionEventPublisher {
    private final List<AuctionEventListener> listeners = new CopyOnWriteArrayList<>();

    public void registerListener(AuctionEventListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(AuctionEventListener listener) {
        listeners.remove(listener);
    }

    public void publish(AuctionEvent event) {
        for (AuctionEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
