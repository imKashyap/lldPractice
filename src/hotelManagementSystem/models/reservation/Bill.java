package hotelManagementSystem.models.reservation;

import java.util.UUID;

public class Bill {
    private final String billId;
    private final Reservation reservation;
    private final double amount;

    public Bill(Reservation reservation, double amount) {
        this.billId = UUID.randomUUID().toString();
        this.reservation = reservation;
        this.amount = amount;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public double getAmount() {
        return amount;
    }

    public String getBillId() {
        return billId;
    }

    @Override
    public String toString() {
        return "Bill [billId=" + billId + ", reservation=" + reservation + ", amount=" + amount + "]";
    }

}
