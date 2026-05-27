package carRentalSystem.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import carRentalSystem.models.Bill;
import carRentalSystem.models.Booking;
import carRentalSystem.models.BookingStatus;
import carRentalSystem.models.Car;
import carRentalSystem.repository.BookingRepository;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final PricingService pricingService;
    private final PaymentService paymentService;
    private final Map<String, Object> locksByCarId = new ConcurrentHashMap<>();

    public BookingService(
            BookingRepository bookingRepository,
            CarService carService,
            CustomerService customerService,
            PricingService pricingService,
            PaymentService paymentService) {
        this.bookingRepository = bookingRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
    }

    public Booking makeReservation(String carId, String customerId, LocalDateTime from, LocalDateTime to) {
        carService.getCar(carId);
        customerService.getCustomer(customerId);

        synchronized (lockForCar(carId)) {
            if (!carService.isAvailable(carId, from, to)) {
                throw new IllegalStateException("Car is not available for the requested date range");
            }
            Booking booking = new Booking("BOOK-" + UUID.randomUUID(), carId, customerId, from, to);
            bookingRepository.save(booking);
            return booking;
        }
    }

    public Booking confirmReservation(String bookingId, String couponCode) {
        Booking booking = findBooking(bookingId);
        synchronized (lockForCar(booking.getCarId())) {
            if (booking.getStatus() != BookingStatus.INITIATED) {
                throw new IllegalStateException("Only initiated bookings can be confirmed");
            }
            Car car = carService.getCar(booking.getCarId());
            double payableAmount = pricingService.calculatePayableAmount(
                    car,
                    booking.getValidFrom(),
                    booking.getValidTo(),
                    couponCode);
            paymentService.processPayment(booking.getCustomerId(), payableAmount);
            booking.reserveCar();
            bookingRepository.save(booking);
            return booking;
        }
    }

    public void cancelReservation(String bookingId) {
        Booking booking = findBooking(bookingId);
        synchronized (lockForCar(booking.getCarId())) {
            booking.cancelBooking();
            bookingRepository.save(booking);
        }
    }

    public Bill dropCar(String bookingId, String couponCode) {
        Booking booking = findBooking(bookingId);
        synchronized (lockForCar(booking.getCarId())) {
            if (booking.getStatus() != BookingStatus.CONFIRMED) {
                throw new IllegalStateException("Only confirmed bookings can be completed");
            }
            Car car = carService.getCar(booking.getCarId());
            double payableAmount = pricingService.calculatePayableAmount(
                    car,
                    booking.getValidFrom(),
                    booking.getValidTo(),
                    couponCode);
            booking.completeBooking();
            bookingRepository.save(booking);
            return new Bill(booking.getId(), payableAmount);
        }
    }

    public Booking findBooking(String bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    private Object lockForCar(String carId) {
        return locksByCarId.computeIfAbsent(carId, ignored -> new Object());
    }
}
