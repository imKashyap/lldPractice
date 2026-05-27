# Movie Ticket Booking System

Design a Movie Ticket Booking System that allows users to browse movies, select showtimes, book seats, and make payments for movie tickets.

The system should support the following operations:

1. Browse movies currently playing and upcoming releases.
2. View movie details, including synopsis, cast, duration, and ratings.
3. Check available showtimes for a selected movie.
4. View seat availability for a selected showtime.
5. Select and book seats for a movie.
6. Apply discounts and offers.
7. Process payments.
8. Generate and manage booking confirmations.

## Requirements

- Users should be able to browse movies by various filters, such as genre, language, date, and time.
- Users should be able to view detailed information about movies.
- Users should be able to check showtimes for a selected movie across different theaters.
- Users should be able to view seat layouts and availability for a selected showtime.
- Users should be able to select multiple seats for booking.
- The system should prevent double booking of seats.
- The system should handle concurrent booking requests to avoid double-booking of seats.
- Users should be able to apply discount codes or offers.
- The system should ensure fair booking opportunities for all users.
- The system should support multiple payment methods.
- The system should generate booking confirmations and send them to users via email or SMS.
- Users should be able to cancel bookings, subject to the cancellation policy.

## Constraints

- A seat can only be booked by one user for a specific showtime.
- Seats are temporarily reserved during the booking process for a limited time, such as 10 minutes.
- Cancellations may be subject to time restrictions and fees.
- The system must handle concurrent booking requests for the same seats.
- The system should maintain accurate seat inventory across multiple booking channels.

## Prep / Design Notes

### Core Models

`User`
- Fields: `userId`, `name`, `email`, `phone`.

`Movie`
- Fields: `movieId`, `title`, `synopsis`, `cast`, `durationInMinutes`, `rating`, `genre`, `language`, `releaseDate`, `status`.

`Theater`
- Fields: `theaterId`, `name`, `address`, `city`, `screens`.

`Screen`
- Fields: `screenId`, `theaterId`, `name`, `seats`.

`Seat`
- Fields: `seatId`, `screenId`, `row`, `number`, `type`.

`Show`
- Fields: `showId`, `movieId`, `theaterId`, `screenId`, `startTime`, `endTime`, `basePrice`, `status`.

`ShowSeat`
- Stores show-specific seat status and price.
- Fields: `showSeatId`, `showId`, `seatId`, `price`, `status`, `lockedByUserId`, `lockExpiresAt`.

`Booking`
- Fields: `bookingId`, `userId`, `showId`, `bookedSeats`, `amount`, `discountAmount`, `status`, `createdAt`, `expiresAt`.

`Payment`
- Fields: `paymentId`, `bookingId`, `amount`, `method`, `status`, `transactionId`.

`Coupon`
- Fields: `couponCode`, `discountType`, `discountValue`, `maxDiscount`, `validFrom`, `validTo`, `active`.

### Relationships

- A theater has many screens.
- A screen has many physical seats.
- A movie has many shows.
- A show runs in one screen and has many show seats.
- A booking belongs to one user and one show.
- A booking contains multiple show seats.
- A payment belongs to one booking.
- A booking confirmation belongs to one confirmed booking.

### Repositories

- `MovieRepository`
- `TheaterRepository`
- `ShowRepository`
- `ShowSeatRepository`
- `BookingRepository`
- `PaymentRepository`
- `CouponRepository`

In-memory repositories use `ConcurrentHashMap`, return snapshot lists for searches, and keep `ShowSeat` objects shared so per-seat locking works.

### Services

`MovieService`
- Adds movies, browses movies by strategy, and returns movie details.

`TheaterService`
- Adds theaters and searches theaters by city.

`ShowService`
- Creates shows, finds shows by movie/theater, and returns seat availability.

`SeatLockService`
- Locks seats, releases seats, and expires old locks.

`BookingService`
- Initiates, confirms, cancels, and expires bookings.

`PricingService`
- Calculates base amount, discount, tax, and final payable amount.

`PaymentService`
- Handles payment and refund flows.

`NotificationService`
- Sends booking confirmation and cancellation notifications.

### Booking Flow

1. User selects movie, theater, show, and seats.
2. `BookingService` asks `SeatLockService` to lock selected seats.
3. `SeatLockService` validates that each seat is available or has an expired lock.
4. Seats are marked `LOCKED` with `lockedByUserId` and `lockExpiresAt`.
5. `PricingService` calculates total amount and applies coupon.
6. `BookingService` creates booking with `INITIATED` status and expiry time.
7. User completes payment.
8. On payment success, seats are marked `BOOKED`.
9. Booking status becomes `CONFIRMED`.
10. `NotificationService` sends confirmation.
11. On payment failure or timeout, locked seats are released.

### Seat Locking And Concurrency

- Seat status is tracked per `ShowSeat`, not on physical `Seat`.
- Lock seats in deterministic order by `showSeatId` to avoid deadlocks.
- Only one thread can transition the same `ShowSeat` from `AVAILABLE` to `LOCKED`.
- A locked seat can be reused only after `lockExpiresAt` has passed.
- Payment confirmation revalidates that seats are locked by the same user.
- Expired bookings release seats back to `AVAILABLE`.
- Confirmed seats are marked `BOOKED`.
- Different shows support concurrent booking independently.

### Search Strategy

`MovieSearchStrategy`
- Implementations: genre, language, date, status.
- Can be combined through a composite strategy with `AND` or `OR` behavior.

`ShowSearchStrategy`
- Implementations: movie, movie and theater, movie and time, city.

`TheaterSearchStrategy`
- Implementation: city search.

### Pricing

`PriceBreakup`
- Fields: `baseAmount`, `convenienceFee`, `taxAmount`, `discountAmount`, `finalAmount`.

`DiscountStrategy`
- Implementations: percentage discount and flat discount.

Pricing rules:
- Use show-seat price.
- Add taxes and convenience fee.
- Validate coupon active date range.
- Cap discount using coupon max discount.

### Cancellation Flow

1. User requests cancellation for a confirmed booking.
2. `CancellationPolicy` checks if cancellation is allowed.
3. Refund amount is calculated based on show start time and policy.
4. `PaymentService` processes refund.
5. Booking status becomes `CANCELLED`.
6. Booked seats are released if policy allows resale.
7. `NotificationService` sends cancellation message.

### Notification Flow

`BookingEventPublisher`
- Subject/event publisher.

`NotificationService`
- Observer/listener.

Events:
- `BookingConfirmedEvent`
- `BookingCancelledEvent`

### Patterns Used

- Repository: persistence abstraction.
- Strategy: movie/show search and discount calculation.
- Composite: combine multiple movie search filters.
- State: booking lifecycle if booking behavior becomes complex.
- Observer/Event: booking confirmation and cancellation notifications.
- Policy: cancellation rules and refund calculation.
- Facade: `MovieTicketBookingSystem` can expose high-level user operations.
- Dependency Injection: services receive repositories and collaborators.
