package parkingLot.payment;

public interface PaymentStrategy {
    boolean makePayment(double amount);
}
