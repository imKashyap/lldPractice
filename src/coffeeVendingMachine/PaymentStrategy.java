package coffeeVendingMachine;

interface PaymentStrategy {
    boolean pay(double amount);
}