package parkingLot.gates;

import parkingLot.ParkingLot;
import parkingLot.ticketing.ParkingTicket;

public class ExitCommand {
        private final ParkingTicket ticket;

        public ExitCommand(ParkingTicket ticket) {
            this.ticket = ticket;
        }

        public void execute() {
            ParkingLot.getInstance().exitVehicle(ticket);
        }
    }
