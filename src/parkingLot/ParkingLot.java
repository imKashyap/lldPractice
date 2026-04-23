package parkingLot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import parkingLot.parking.ParkingFloor;
import parkingLot.parking.ParkingSpot;
import parkingLot.payment.PaymentStrategy;
import parkingLot.ticketing.FeeCalculatorStrategy;
import parkingLot.ticketing.ParkingReceipt;
import parkingLot.ticketing.ParkingTicket;
import parkingLot.ticketing.nominal.BasicFeeCalculator;
import parkingLot.vehicle.Vehicle;

public class ParkingLot {
    private static final ParkingLot INSTANCE = new ParkingLot();
    private final List<ParkingFloor> floors = new ArrayList<>();
    private FeeCalculatorStrategy feeCalculator = new BasicFeeCalculator();

    private ParkingLot() {
    }

    public static ParkingLot getInstance() {
        return INSTANCE;
    }

    public void setFeeCalculator(FeeCalculatorStrategy feeCalculator) {
        this.feeCalculator = feeCalculator;
    }

    public void addfloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.findAvailableSpot(vehicle.getType());
            if (spot.isPresent()) {
                ParkingTicket.Builder ticket = spot.get().assignVehicle(vehicle);
                floor.notifyObservers();
                return ticket
                        .setparkingFloor(floor)
                        .build();
            }
        }
        throw new RuntimeException("No available spot");
    }

    public void exitVehicle(ParkingTicket ticket, PaymentStrategy paymentStrategy) {
        ParkingSpot spot = ticket.getParkingSpot();
        spot.release();
        ParkingReceipt parkingReceipt = new ParkingReceipt(ticket, feeCalculator, paymentStrategy);
        parkingReceipt.generateReceipt();
    }
}
