package movieTicketBookingSystem.model;

public class Payment {
    private final String paymentId;
    private final String bookingId;
    private final double amount;
    private final PaymentMethod method;
    private PaymentStatus status;
    private final String transactionId;

    public Payment(
            String paymentId,
            String bookingId,
            double amount,
            PaymentMethod method,
            PaymentStatus status,
            String transactionId) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionId = transactionId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public synchronized PaymentStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
