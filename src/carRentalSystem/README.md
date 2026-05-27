# Designing a Car Rental System

## Requirements
1. The car rental system should allow customers to browse and reserve available cars for specific dates.
2. Each car should have details such as make, model, year, license plate number, and rental price per day.
3. Customers should be able to search for cars based on various criteria, such as car type, price range, and availability.
4. The system should handle reservations, including creating, modifying, and canceling reservations.
5. The system should keep track of the availability of cars and update their status accordingly.
6. The system should handle customer information, including name, contact details, and driver's license information.
7. The system should handle payment processing for reservations.
8. The system should be able to handle concurrent reservations and ensure data consistency.

## Prep / Design Notes

### Core Models

`Car`
- Fields: `id`, `make`, `model`, `modelYear`, `carType`, `licensePlateNo`

`CarType`
- Defines base rental price per day for each type: `HATCHBACK`, `MICRO`, `PICKUP`, `SEDAN`, `OFFROAD`, `SPORT`, `SUV`

`Customer`
- Fields: `id`, `name`, `phoneNo`, `licenseId`

`Booking`
- Fields: `id`, `carId`, `customerId`, `validFrom`, `validTo`, `status`, `state`
- Exposes lifecycle operations: `reserveCar`, `cancelBooking`, `completeBooking`
- Uses date range overlap checks to prevent double booking

`BookingStatus`
- Values: `INITIATED`, `CONFIRMED`, `CANCELLED`, `COMPLETED`

`Bill`
- Fields: `bookingId`, `amount`

`Coupon`
- Fields: `id`, `couponCode`, `discountValue`, `strategy`

### Repositories

`CarRepository`
- `save(Car)`
- `findById(id)`
- `search(CarSearchStrategy)`
- `findAll()`

`CustomerRepository`
- `save(Customer)`
- `findById(id)`
- `findAll()`

`BookingRepository`
- `save(Booking)`
- `findById(id)`
- `findAll()`
- `findByCarId(carId)`

`CouponRepository`
- `save(Coupon)`
- `findByCode(couponCode)`

In-memory repositories use `ConcurrentHashMap` for thread-safe storage.

### Services

`CustomerService`
- Adds and fetches customers.

`CarService`
- Adds cars.
- Searches cars by type, price range, and availability.
- Checks availability by rejecting overlapping `INITIATED` or `CONFIRMED` bookings.

`BookingService`
- Creates reservations.
- Confirms reservations after pricing and payment.
- Cancels reservations.
- Completes reservations and generates a bill.
- Uses per-car locks to keep reservation updates consistent under concurrent requests.

`PricingService`
- Calculates gross rental amount from car type base price and rental days.
- Applies coupon strategies.
- Adds tax to calculate payable amount.

`PaymentService`
- Handles payment processing abstraction.

### Strategies

`CarSearchStrategy`
- Implementations: search by type, search by price range.

`CouponDiscountStrategy`
- Implementations: flat discount, percentage discount with max cap.

### State Pattern

`BookingState`
- `BookingInitiatedState`: can confirm or cancel.
- `BookingConfirmedState`: can cancel or complete.
- `BookingCancelledState`: rejects further transitions.
- `BookingCompletedState`: rejects further transitions.

### Facade

`CarRentalSystem`
- Provides the high-level API for adding cars/customers, searching availability, reserving cars, confirming reservations, cancelling reservations, and returning cars.

### Patterns Used

- Repository: persistence abstraction.
- Strategy: car search and coupon discount calculation.
- State: booking lifecycle validation.
- Facade: `CarRentalSystem` top-level API.
- Dependency Injection: services receive repositories and collaborators.
- Per-resource locking: prevents concurrent double booking for the same car.
