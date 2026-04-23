package parkingLot.payment;

public class CardPayment implements PaymentStrategy{

    @Override
    public boolean makePayment(double amount) {
        System.out.println("Card Payment done for Rs "+ amount);
        return true;
    }

}
