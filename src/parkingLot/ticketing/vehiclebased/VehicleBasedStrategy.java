package parkingLot.ticketing.vehiclebased;

import parkingLot.ticketing.FeeCalculatorStrategy;
import parkingLot.ticketing.ParkingTicket;
import parkingLot.vehicle.VehicleType;

public class VehicleBasedStrategy implements FeeCalculatorStrategy {
    @Override
    public double calculateFee(ParkingTicket ticket) {
        switch (ticket.getVehicle().getType()) {
            case VehicleType.CAR:
                return 200;
            case VehicleType.BIKE:
                return 100;
            case VehicleType.TRUCK:
                return 300;
            case VehicleType.EV:
                return 450;
            default:
                throw new IllegalArgumentException("Unknown vehicle type");
        }
    }

}
