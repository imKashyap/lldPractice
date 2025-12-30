package coffeeVendingMachine;

class UPIPayment implements PaymentStrategy {
    public boolean pay(double amount) {
        System.out.println("Paid ₹" + amount + " via UPI");
        return true;
    }
}
