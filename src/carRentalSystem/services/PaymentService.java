package carRentalSystem.services;

public class PaymentService {

    public boolean processPayment(String customerId, double amount) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer id is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        return true;
    }
}
