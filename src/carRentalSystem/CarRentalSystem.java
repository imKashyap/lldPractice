package carRentalSystem;

import java.time.LocalDateTime;
import java.util.List;

import carRentalSystem.models.Bill;
import carRentalSystem.models.Booking;
import carRentalSystem.models.Car;
import carRentalSystem.models.CarType;
import carRentalSystem.models.Customer;
import carRentalSystem.services.BookingService;
import carRentalSystem.services.CarService;
import carRentalSystem.services.CustomerService;

public class CarRentalSystem {
    private final CustomerService customerService;
    private final CarService carService;
    private final BookingService bookingService;

    public CarRentalSystem(CustomerService customerService, CarService carService, BookingService bookingService) {
        this.customerService = customerService;
        this.carService = carService;
        this.bookingService = bookingService;
    }

    public void addCustomer(Customer customer) {
        customerService.addCustomer(customer);
    }

    public void addCar(Car car) {
        carService.addCar(car);
    }

    public List<Car> searchCarsByType(CarType carType) {
        return carService.searchByType(carType);
    }

    public List<Car> searchAvailableCars(LocalDateTime from, LocalDateTime to) {
        return carService.searchAvailable(from, to);
    }

    public Booking reserveCar(String carId, String customerId, LocalDateTime from, LocalDateTime to) {
        return bookingService.makeReservation(carId, customerId, from, to);
    }

    public Booking confirmReservation(String bookingId, String couponCode) {
        return bookingService.confirmReservation(bookingId, couponCode);
    }

    public void cancelReservation(String bookingId) {
        bookingService.cancelReservation(bookingId);
    }

    public Bill returnCar(String bookingId, String couponCode) {
        return bookingService.dropCar(bookingId, couponCode);
    }
}
