package parkingLot.payment;

public class CashPayment implements PaymentStrategy{

    @Override
    public boolean makePayment(double amount) {
        System.out.println("Cash Payment Done for Rs "+ amount);
        return true;
    }

}
