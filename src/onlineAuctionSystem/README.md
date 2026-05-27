# Designing an Online Auction System
This system allows for the creation and management of auctions, user participation in bidding, and handling transactions.

## Requirements
1. The online auction system should allow users to register and log in to their accounts.
2. Users should be able to create new auction listings with details such as item name, description, starting price, and auction duration.
3. Users should be able to browse and search for auction listings based on various criteria (e.g., item name, category, price range).
4. Users should be able to place bids on active auction listings.
5. The system should automatically update the current highest bid and notify the bidders accordingly.
6. The auction should end when the specified duration is reached, and the highest bidder should be declared the winner.
7. The system should handle concurrent access to auction listings and ensure data consistency.
8. The system should be extensible to accommodate future enhancements and new features.

## Prep / Design Notes

### Core Models

`Account`
- Fields: `username`, `password`

`User`
- Fields: `userId`, `name`, `account`

`Item`
- Fields: `itemId`, `name`, `description`, `category`

`Auction`
- Fields: `auctionId`, `item`, `sellerId`, `startingPrice`, `startTime`, `endTime`, `status`, `state`, `currentHighestBidId`, `winningBid`

`Bid`
- Fields: `bidId`, `auctionId`, `bidderId`, `amount`, `createdAt`

### Relationships

- A user creates auctions as a seller.
- An auction owns one item.
- A bid references an auction by `auctionId`.
- A bid references a user by `bidderId`.
- Auction stores `currentHighestBidId` for quick highest-bid lookup.
- Notifications are handled through event publisher/listener instead of direct subject/observer logic on models.

### State Pattern

`AuctionState`
- `placeBid(Auction, Bid, currentHighestBid)`
- `close(Auction)`
- `cancel(Auction)`

`ActiveAuctionState`
- Rejects expired auctions.
- Rejects seller bidding on own auction.
- Rejects bids below starting price or current highest bid.
- Accepts valid bids.

`ClosedAuctionState`
- Rejects bidding and cancellation.

`CancelledAuctionState`
- Rejects bidding and closing.

### Repositories

`UserRepository`
- `save(User)`
- `findByUsername(username)`

`AuctionRepository`
- `save(Auction)`
- `find(AuctionSearchStrategy)`

`BidRepository`
- `save(Bid)`
- `find(BidSearchStrategy)`

In-memory repositories use `ConcurrentHashMap` and search on snapshot copies.

### Services

`UserService`
- Registers and finds users.

`AuctionService`
- Creates auctions.
- Searches auctions by id, name, category, and price range.
- Closes auctions.

`BidService`
- Finds auction.
- Synchronizes on auction object.
- Creates bid.
- Delegates validation to auction state.
- Saves bid and updates highest bid.
- Publishes bid placed event.
- Subscribes bidder to the auction.

### Notification Flow

`AuctionEventPublisher`
- Subject/event publisher.

`NotificationService`
- Observer/listener.

`BidPlacedEvent`
- Event emitted after successful bid placement.

`AuctionSubscriptionRegistry`
- Stores auction subscribers and prevents duplicate subscriptions.

### Search Strategy

`AuctionSearchStrategy`
- Implementations: search by auction id, name, category, and price range.

`BidSearchStrategy`
- Implementation: search by bid id.

### Concurrency

- Bid placement locks per auction object.
- Different auctions can receive bids concurrently.
- Auction mutable fields are updated inside the auction lock.
- Events are published after releasing the auction lock.
- Event publisher uses a thread-safe listener list.

### Patterns Used

- Repository: storage abstraction.
- Strategy: auction and bid search.
- State: auction lifecycle behavior.
- Observer/Event: notifications.
- Dependency Injection: services receive shared dependencies.
