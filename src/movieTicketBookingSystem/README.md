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
