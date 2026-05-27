class Car:
    - carId
    - make
    - modelName
    - modelYear
    - licensePlateNo
    - carType

enum CarType:
    - VAN (2K)
    - MICRO (2.5K)
    - HATCHBACK (3.5K)
    - PICKUP (4K)
    - SEDAN (4.5K)
    - OFFROAD (5K)
    - SPORT (5.5K)
    - SUV (6K)


Customer
    - id
    - name
    - ContactDetail
    - licenseId

Contact Details
    - phoneNo
    - email
    - address

Reservation
    - reservationId
    - Car
    - Customer
    - validFrom
    - validTo
    - status
    + setStatus(ReservationStatus) -> void
    + setValidFrom(validFrom) -> void
    + setValidTo(validTo) -> void

enum ReservationStatus:
    - CREATED
    - CONFIRMED
    - CANCELLED
    - COMPLETED
    - EXPIRED

class Bill
    - Reservation
    - amount


interface CarRepository:
    + save(Car) -> void
    + getCar(CarSearchStrategy) -> Copy Of List<Car>
    + updateCar(carId)

class InMemoryCarRepository implements CarRepository:
    - Map<CarId, Car>

interface CarSearchStrategy:
    + search(List<Car>) -> List<Car>

class CarSearchByCarType implements CarSearchStrategy:
    - requiredCarType

class CarSearchByPriceRange implements CarSearchStrategy:
    - minAmount
    - maxAmount

class CarSearchByAvailability implements CarSearchStrategy:

interface CustomerRepository:
    + save(Customer) -> void

class InMemoryCustomerRepository implements CustomerRepository:
    - Map<CustomerId,Customer>

interface ReservationRepository:
    + save(Reservation) -> void
    + findById(reservationId) -> Reservation

class InMemoryReservationRepository implements ReservationRepository:
    - Map<ReservationId, Reservation>

class CustomerService:
    - CustomerRepository
    + addCustomer(Customer) -> void

class CarService:
    - CarRepository
    + addCar(Car) -> void
    + searchCarByCarType(CarType) -> List<Car>
    + searchCarByPriceRange(minAmount, maxAmount) -> List<Car>
    + searchCarByAvailability() -> List<Car>

class ReservationService:
    - ReservationRepository
    - CarRepository
    + makeReservation(Car car, ValidFrom, validTo, Customer)
    + updateValidFrom(reservationId) -> void
    + updateValidTo(reservationId) -> void
    + cancelReservation(reservationId) -> void
    + pickCar(reservationId)
    + dropCar(reservationId) -> Bill

class AvailabilityService:
    - CarRepository
    findCarByDateRange

PricingService
- calculates rental cost
- applies car type rate, date count, discounts, taxes

PaymentService
- handles payment authorization/capture/refund


class RentalSystem
    - CustomerService
    - CarService
    - ReservationService
