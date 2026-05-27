package carRentalSystem;

import java.time.LocalDateTime;

import carRentalSystem.models.Bill;
import carRentalSystem.models.Booking;
import carRentalSystem.models.Car;
import carRentalSystem.models.CarType;
import carRentalSystem.models.Coupon;
import carRentalSystem.models.Customer;
import carRentalSystem.repository.BookingRepository;
import carRentalSystem.repository.CarRepository;
import carRentalSystem.repository.CouponRepository;
import carRentalSystem.repository.CustomerRepository;
import carRentalSystem.repository.inmemory.InMemoryBookingRepository;
import carRentalSystem.repository.inmemory.InMemoryCarRepository;
import carRentalSystem.repository.inmemory.InMemoryCouponRepository;
import carRentalSystem.repository.inmemory.InMemoryCustomerRepository;
import carRentalSystem.services.BookingService;
import carRentalSystem.services.CarService;
import carRentalSystem.services.CustomerService;
import carRentalSystem.services.PaymentService;
import carRentalSystem.services.PricingService;
import carRentalSystem.utils.coupon.PercentageDiscountStrategy;

public class CarRentalSystemDemo {
    public static void main(String[] args) {
        CarRepository carRepository = new InMemoryCarRepository();
        CustomerRepository customerRepository = new InMemoryCustomerRepository();
        BookingRepository bookingRepository = new InMemoryBookingRepository();
        CouponRepository couponRepository = new InMemoryCouponRepository();

        CustomerService customerService = new CustomerService(customerRepository);
        CarService carService = new CarService(carRepository, bookingRepository);
        PricingService pricingService = new PricingService(couponRepository);
        PaymentService paymentService = new PaymentService();
        BookingService bookingService = new BookingService(
                bookingRepository,
                carService,
                customerService,
                pricingService,
                paymentService);

        CarRentalSystem carRentalSystem = new CarRentalSystem(customerService, carService, bookingService);

        couponRepository.save(new Coupon("CPN-1", "SAVE10", "10", new PercentageDiscountStrategy(1000)));
        carRentalSystem.addCar(new Car("CAR-1", "Toyota", "Fortuner", "2024", CarType.SUV, "KA-01-AA-1111"));
        carRentalSystem.addCar(new Car("CAR-2", "Honda", "City", "2023", CarType.SEDAN, "KA-01-BB-2222"));
        carRentalSystem.addCustomer(new Customer("CUS-1", "Aarav", "9999999999", "DL-123"));

        LocalDateTime from = LocalDateTime.now().plusDays(1);
        LocalDateTime to = from.plusDays(3);

        System.out.println("Available cars: " + carRentalSystem.searchAvailableCars(from, to).size());
        Booking booking = carRentalSystem.reserveCar("CAR-1", "CUS-1", from, to);
        carRentalSystem.confirmReservation(booking.getId(), "SAVE10");
        System.out.println("Booking status after confirmation: " + booking.getStatus());

        Bill bill = carRentalSystem.returnCar(booking.getId(), "SAVE10");
        System.out.println("Booking status after return: " + booking.getStatus());
        System.out.println("Final bill amount: " + bill.getAmount());
    }
}
