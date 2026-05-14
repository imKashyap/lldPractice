# Online Auction System Prep

## Requirements
- Register/login users.
- Create auctions for items.
- Search auctions by name, category, and price range.
- Place bids on active auctions.
- Update highest bid and notify subscribers.
- Close auction after end time and declare winner.
- Handle concurrent bidding safely.

## Core Models

class Account:
    - username
    - password

class User:
    - name
    - Account account

class Item:
    - itemId
    - name
    - description
    - ItemCategory category

enum ItemCategory:
    - ARTWORK
    - ANTIQUES
    - MEMORABILIA
    - ORNAMENTS

class Auction:
    - auctionId
    - Item item
    - sellerId
    - startingPrice
    - startTime
    - endTime
    - AuctionStatus status
    - AuctionState state
    - currentHighestBidId
    - winningBid

enum AuctionStatus:
    - ACTIVE
    - CANCELLED
    - CLOSED

class Bid:
    - bidId
    - auctionId
    - bidderId
    - amount
    - createdAt

## Relationships
- User creates Auction as seller.
- Auction owns Item.
- Bid references Auction by auctionId.
- Bid references User by bidderId.
- Auction stores currentHighestBidId for quick highest-bid lookup.
- Auction/User do not directly implement Subject/Observer.
- Notifications are handled through event publisher/listener.

## State Pattern

interface AuctionState:
    + placeBid(Auction, Bid, currentHighestBid): void
    + close(Auction): void
    + cancel(Auction): void

class ActiveAuctionState:
    - rejects expired auction
    - rejects seller bidding on own auction
    - rejects amount <= current highest bid / starting price
    - accepts valid bid

class ClosedAuctionState:
    - rejects bid/cancel
    - close is no-op / already closed

class CancelledAuctionState:
    - rejects bid/close
    - cancel is no-op / already cancelled

## Repositories

interface UserRepository:
    + save(User): void
    + findByUsername(username): Optional<User>

interface AuctionRepository:
    + save(Auction): void
    + find(AuctionSearchStrategy): List<Auction>

interface BidRepository:
    + save(Bid): void
    + find(BidSearchStrategy): List<Bid>

In-memory repositories:
    - use ConcurrentHashMap
    - search on List.copyOf(map.values()) snapshot

## Services

class UserService:
    - UserRepository
    + registerUser(User): void
    + findUser(username): Optional<User>

class AuctionService:
    - AuctionRepository
    - AuctionSubscriptionRegistry
    + createAuction(Auction): void
    + findAuctionById(auctionId): Auction
    + findAuctionItemByName(name): List<Auction>
    + findAuctionItemByCategory(category): List<Auction>
    + findAuctionItemByPriceRange(min, max): List<Auction>
    + closeAuction(auctionId): void

class BidService:
    - BidRepository
    - AuctionRepository
    - AuctionEventPublisher
    - AuctionSubscriptionRegistry
    + placeBid(bidId, auctionId, bidderId, amount): void

BidService flow:
    1. find auction
    2. synchronize on auction
    3. create bid
    4. fetch current highest bid
    5. delegate validation to AuctionState
    6. save bid
    7. update currentHighestBidId
    8. publish BidPlacedEvent
    9. subscribe bidder to auction

## Notification Flow

interface AuctionEvent

class BidPlacedEvent implements AuctionEvent:
    - auctionId
    - bidderId
    - bidAmount
    - previousHighestBidderId

interface AuctionEventListener:
    + onEvent(AuctionEvent): void

class AuctionEventPublisher:
    - List<AuctionEventListener> listeners
    + registerListener(listener): void
    + unregisterListener(listener): void
    + publish(event): void

class NotificationService implements AuctionEventListener:
    - AuctionSubscriptionRegistry
    - UserRepository
    + onEvent(AuctionEvent): void

class AuctionSubscriptionRegistry:
    - Map<auctionId, Set<userId>> subscribers
    + subscribe(auctionId, userId): void
    + getSubscribers(auctionId): List<userId>

Observer/Event mapping:
    - Subject: AuctionEventPublisher
    - Observer: NotificationService
    - Event: BidPlacedEvent

## Search Strategy

interface AuctionSearchStrategy:
    + search(List<Auction>): List<Auction>

class SearchByAuctionIdStrategy
class SearchByNameStrategy
class SearchByCategoryStrategy
class SearchByPriceRangeStrategy

interface BidSearchStrategy:
    + search(List<Bid>): List<Bid>

class SearchByBidIdStrategy

## Concurrency
- Repositories use ConcurrentHashMap.
- Search uses snapshot copy to avoid iterating over a changing map view.
- Bid placement locks per Auction object.
- Only one thread can update highest bid for the same auction at a time.
- Different auctions can receive bids concurrently.
- Auction mutable fields like status/state/currentHighestBidId should be volatile or updated inside auction lock.
- Subscription registry is synchronized and stores subscribers in Set to avoid duplicates.
- Event publisher uses a thread-safe listener list.
- Events are published after releasing the auction lock.

## Patterns Used
- Repository: storage abstraction.
- Strategy: auction/bid search.
- State: auction lifecycle behavior.
- Observer/Event: notifications.
- Dependency Injection: services receive shared dependencies.
