package parkingLot;

import parkingLot.gates.EntryGate;
import parkingLot.gates.ExitGate;
import parkingLot.parking.DisplayBoard;
import parkingLot.parking.Observer;
import parkingLot.parking.ParkingFloor;
import parkingLot.parking.ParkingSpot;
import parkingLot.ticketing.ParkingTicket;
import parkingLot.ticketing.WeekendFeeCalculator;
import parkingLot.vehicle.Vehicle;
import parkingLot.vehicle.VehicleFactory;
import parkingLot.vehicle.VehicleType;

public class ParkingLotDemo {
    public static void main(String[] args) throws InterruptedException {
        ParkingLot lot = ParkingLot.getInstance();

        // Change fee strategy for weekend if needed
        if (java.time.LocalDate.now().getDayOfWeek().getValue() >= 6) { // Saturday or Sunday
            lot.setFeeCalculator(new WeekendFeeCalculator());
        }

        Observer displayBoard = new DisplayBoard();
        ParkingFloor floor1 = new ParkingFloor("L1");
        floor1.addParkingSpot(new ParkingSpot( VehicleType.CAR));
        floor1.addParkingSpot(new ParkingSpot(VehicleType.BIKE));
        floor1.addObserver(displayBoard);
        lot.addfloor(floor1);

        EntryGate entryGate = new EntryGate();
        ExitGate exitGate = new ExitGate();

        Vehicle car = VehicleFactory.createVehicle("KA01AB1234", VehicleType.CAR);
        Vehicle bike = VehicleFactory.createVehicle("KA02XY5678", VehicleType.BIKE);

        ParkingTicket carTicket = entryGate.enterVehicle(car);
        Thread.sleep(2000);
        ParkingTicket bikeTicket = entryGate.enterVehicle(bike);

        Thread.sleep(5000); // simulate time parked

        exitGate.exitVehicle(carTicket);
        exitGate.exitVehicle(bikeTicket);
    }
}
