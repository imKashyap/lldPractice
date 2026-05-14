package onlineAuctionSystem.utils;

import onlineAuctionSystem.models.auction.AuctionEvent;

public interface AuctionEventListener {
    void onEvent(AuctionEvent event);
}
