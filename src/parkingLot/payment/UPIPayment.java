package parkingLot.payment;

public class UPIPayment implements PaymentStrategy{

    @Override
    public boolean makePayment(double amount) {
        System.out.println("UPI payment done for Rs "+ amount);
        return true;
    }

}
