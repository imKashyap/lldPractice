# Movie Ticket Booking System Prep

## Core Models

class User:
    - userId
    - name
    - email
    - phone

class Movie:
    - movieId
    - title
    - synopsis
    - cast
    - durationInMinutes
    - rating
    - genre
    - language
    - releaseDate
    - MovieStatus status

enum MovieStatus:
    - NOW_SHOWING
    - UPCOMING
    - INACTIVE

class Theater:
    - theaterId
    - name
    - address
    - city
    - List<Screen> screens

class Screen:
    - screenId
    - theaterId
    - name
    - List<Seat> seats

class Seat:
    - seatId
    - screenId
    - row
    - number
    - SeatType type

enum SeatType:
    - REGULAR
    - PREMIUM
    - RECLINER

class Show:
    - showId
    - movieId
    - theaterId
    - screenId
    - startTime
    - endTime
    - basePrice
    - ShowStatus status

enum ShowStatus:
    - SCHEDULED
    - CANCELLED
    - COMPLETED

class ShowSeat:
    - showSeatId
    - showId
    - seatId
    - price
    - SeatBookingStatus status
    - lockedByUserId
    - lockExpiresAt

enum SeatBookingStatus:
    - AVAILABLE
    - LOCKED
    - BOOKED
    - BLOCKED

class Booking:
    - bookingId
    - userId
    - showId
    - List<showSeatId> bookedSeats
    - amount
    - discountAmount
    - BookingStatus status
    - createdAt
    - expiresAt

enum BookingStatus:
    - INITIATED
    - CONFIRMED
    - CANCELLED
    - EXPIRED
    - PAYMENT_FAILED

class Payment:
    - paymentId
    - bookingId
    - amount
    - PaymentMethod method
    - PaymentStatus status
    - transactionId

enum PaymentMethod:
    - CARD
    - UPI
    - WALLET
    - NET_BANKING

enum PaymentStatus:
    - INITIATED
    - SUCCESS
    - FAILED
    - REFUNDED

class Coupon:
    - couponCode
    - discountType
    - discountValue
    - maxDiscount
    - validFrom
    - validTo
    - active

class BookingConfirmation:
    - confirmationId
    - bookingId
    - message
    - sentAt

## Relationships

- A `Theater` has many `Screen`.
- A `Screen` has many `Seat`.
- A `Movie` has many `Show`.
- A `Show` runs in one `Screen`.
- A `Show` has many `ShowSeat` entries, one per physical seat.
- `ShowSeat` stores show-specific seat status and price.
- A `Booking` belongs to one `User`.
- A `Booking` belongs to one `Show`.
- A `Booking` contains multiple `ShowSeat`.
- A `Payment` belongs to one `Booking`.
- `BookingConfirmation` belongs to one confirmed `Booking`.

## Repositories

interface MovieRepository:
    + save(Movie): void
    + find(MovieSearchStrategy): List<Movie>
    + findById(movieId): Optional<Movie>

interface TheaterRepository:
    + save(Theater): void
    + find(TheaterSearchStrategy): List<Theater>
    + findById(theaterId): Optional<Theater>

interface ShowRepository:
    + save(Show): void
    + find(ShowSearchStrategy): List<Show>
    + findById(showId): Optional<Show>

interface ShowSeatRepository:
    + save(ShowSeat): void
    + findByShowId(showId): List<ShowSeat>
    + findByShowIdAndSeatIds(showId, seatIds): List<ShowSeat>

interface BookingRepository:
    + save(Booking): void
    + findById(bookingId): Optional<Booking>
    + findByUserId(userId): List<Booking>

interface PaymentRepository:
    + save(Payment): void
    + findByBookingId(bookingId): Optional<Payment>

interface CouponRepository:
    + findByCode(couponCode): Optional<Coupon>

In-memory repositories:
    - use ConcurrentHashMap
    - return List.copyOf(map.values()) for search snapshots
    - keep ShowSeat objects shared so per-seat locking works

## Services

class MovieService:
    - MovieRepository
    + addMovie(Movie): void
    + browseMovies(MovieSearchStrategy): List<Movie>
    + getMovieDetails(movieId): Movie

class TheaterService:
    - TheaterRepository
    + addTheater(Theater): void
    + findTheatersByCity(city): List<Theater>

class ShowService:
    - ShowRepository
    - ShowSeatRepository
    + createShow(Show, seats): void
    + findShowsByMovie(movieId): List<Show>
    + findShowsByMovieAndCity(movieId, city): List<Show>
    + getSeatAvailability(showId): List<ShowSeat>

class SeatLockService:
    - ShowSeatRepository
    + lockSeats(showId, seatIds, userId): void
    + releaseSeats(showId, seatIds, userId): void
    + expireLocks(): void

class BookingService:
    - BookingRepository
    - ShowRepository
    - ShowSeatRepository
    - SeatLockService
    - PricingService
    + initiateBooking(userId, showId, seatIds, couponCode): Booking
    + confirmBooking(bookingId): Booking
    + cancelBooking(bookingId): void
    + expireBooking(bookingId): void

class PricingService:
    - CouponRepository
    + calculateAmount(showId, seatIds, couponCode): PriceBreakup
    + applyCoupon(amount, couponCode): Discount

class PaymentService:
    - PaymentRepository
    - BookingRepository
    + pay(bookingId, paymentMethod): Payment
    + refund(bookingId): Payment

class NotificationService:
    + sendBookingConfirmation(bookingId): void
    + sendCancellation(bookingId): void

class CancellationPolicy:
    + canCancel(Booking, Show): boolean
    + calculateRefundAmount(Booking, Show): amount

## Booking Flow

1. User selects movie, theater, show, and seats.
2. `BookingService` asks `SeatLockService` to lock selected seats.
3. `SeatLockService` validates that each seat is `AVAILABLE` or has an expired lock.
4. Seats are marked `LOCKED` with `lockedByUserId` and `lockExpiresAt`.
5. `PricingService` calculates total amount and applies coupon.
6. `BookingService` creates booking with `INITIATED` status and expiry time.
7. User completes payment through `PaymentService`.
8. On payment success, `BookingService` marks seats as `BOOKED`.
9. Booking status becomes `CONFIRMED`.
10. `NotificationService` sends booking confirmation.
11. On payment failure or timeout, locked seats are released.

## Seat Locking And Concurrency

- Seat status is tracked per `ShowSeat`, not on physical `Seat`.
- Lock seats in a deterministic order by `showSeatId` to avoid deadlocks.
- Synchronize on each `ShowSeat` or use per-show lock during seat selection.
- Only one thread can transition the same `ShowSeat` from `AVAILABLE` to `LOCKED`.
- A locked seat can be reused only after `lockExpiresAt` has passed.
- Payment confirmation must revalidate that seats are locked by the same user.
- Expired bookings release seats back to `AVAILABLE`.
- Confirmed seats are marked `BOOKED` and cannot be released except through cancellation.
- Different shows should support concurrent booking independently.

## Search Strategy

interface MovieSearchStrategy:
    + search(List<Movie>): List<Movie>

class CompositeMovieSearchStrategy implements MovieSearchStrategy:
    - List<MovieSearchStrategy> strategies
    - SearchOperator operator
    + addStrategy(MovieSearchStrategy): void
    + search(List<Movie>): List<Movie>

enum SearchOperator:
    - AND
    - OR

class SearchMovieByGenreStrategy
class SearchMovieByLanguageStrategy
class SearchMovieByDateStrategy
class SearchMovieByStatusStrategy

Composite movie search flow:
    1. Create one strategy per selected filter.
    2. Add selected strategies to CompositeMovieSearchStrategy.
    3. Choose `AND` when movies must match all selected filters.
    4. Choose `OR` when movies can match any selected filter.
    5. Return combined results.

AND example:
    - genre = ACTION
    - language = HINDI
    - status = NOW_SHOWING
    - result = movies matching action AND Hindi AND now showing

OR example:
    - genre = ACTION
    - language = HINDI
    - result = movies matching action OR Hindi

Composite implementation notes:
    - AND can apply each strategy on the previous result.
    - OR should apply each strategy on the original movie list and merge unique results.
    - use movieId to de-duplicate OR results.
    - an empty composite can return all movies or no movies based on product behavior.

interface ShowSearchStrategy:
    + search(List<Show>): List<Show>

class SearchShowByMovieStrategy
class SearchShowByMovieAndTheaterStrategy
class SearchShowByMovieAndTimeStrategy
class SearchShowByCityStrategy

interface TheaterSearchStrategy:
    + search(List<Theater>): List<Theater>

class SearchTheaterByCityStrategy

## Pricing

class PriceBreakup:
    - baseAmount
    - convenienceFee
    - taxAmount
    - discountAmount
    - finalAmount

interface DiscountStrategy:
    + apply(amount, Coupon): Discount

class PercentageDiscountStrategy
class FlatDiscountStrategy

Pricing rules:
    - use seat type price from ShowSeat
    - add taxes and convenience fee
    - validate coupon active date range
    - cap discount using coupon maxDiscount

## Cancellation Flow

1. User requests cancellation for a confirmed booking.
2. `CancellationPolicy` checks if cancellation is allowed.
3. Refund amount is calculated based on show start time and policy.
4. `PaymentService` processes refund.
5. Booking status becomes `CANCELLED`.
6. Booked seats are released to `AVAILABLE` if policy allows resale.
7. `NotificationService` sends cancellation message.

## Notification Flow

interface BookingEvent

class BookingConfirmedEvent implements BookingEvent:
    - bookingId
    - userId
    - showId

class BookingCancelledEvent implements BookingEvent:
    - bookingId
    - userId
    - refundAmount

interface BookingEventListener:
    + onEvent(BookingEvent): void

class BookingEventPublisher:
    - List<BookingEventListener> listeners
    + registerListener(listener): void
    + publish(event): void

class NotificationService implements BookingEventListener:
    + onEvent(BookingEvent): void

Observer/Event mapping:
    - Subject: BookingEventPublisher
    - Observer: NotificationService
    - Events: BookingConfirmedEvent, BookingCancelledEvent

## Patterns Used

- Repository: persistence abstraction.
- Strategy: movie/show search and discount calculation.
- Composite: combine multiple movie search filters.
- State: booking lifecycle if booking behavior becomes complex.
- Observer/Event: booking confirmation and cancellation notifications.
- Policy: cancellation rules and refund calculation.
- Facade: `MovieTicketBookingSystem` can expose high-level user operations.
- Dependency Injection: services receive repositories and collaborators.
