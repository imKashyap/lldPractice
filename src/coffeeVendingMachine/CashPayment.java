package coffeeVendingMachine;

class CashPayment implements PaymentStrategy {
    public boolean pay(double amount) {
        System.out.println("Paid ₹" + amount + " in cash");
        return true;
    }
}