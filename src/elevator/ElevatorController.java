package elevator;

import java.util.Comparator;

public class ElevatorController {
    private final Building building;

    public ElevatorController(Building building) {
        this.building = building;
    }

    public synchronized Elevator requestElevator(int sourceFloor, Direction direction) {
        building.validateFloor(sourceFloor);
        ExternalRequest request = new ExternalRequest(sourceFloor, direction);
        Elevator elevator = chooseBestElevator(request);
        elevator.addStop(sourceFloor);
        System.out.println("Assigned elevator " + elevator.getId() + " to floor " + sourceFloor + " " + direction);
        return elevator;
    }

    public synchronized void selectDestination(int elevatorId, int destinationFloor) {
        building.validateFloor(destinationFloor);
        InternalRequest request = new InternalRequest(elevatorId, destinationFloor);
        Elevator elevator = building.getElevator(request.getElevatorId());
        elevator.addStop(request.getDestinationFloor());
    }

    public synchronized void step() {
        for (Elevator elevator : building.getElevators()) {
            elevator.moveToNextStop();
        }
    }

    public synchronized void runUntilIdle() {
        while (!allElevatorsIdle()) {
            step();
        }
    }

    private Elevator chooseBestElevator(ExternalRequest request) {
        return building.getElevators().stream()
                .filter(elevator -> elevator.canServeOnCurrentPath(request))
                .min(Comparator.comparingInt((Elevator elevator) -> elevator.distanceFrom(request.getSourceFloor()))
                        .thenComparingInt(Elevator::pendingStopsCount))
                .orElseGet(() -> building.getElevators().stream()
                        .min(Comparator.comparingInt(Elevator::pendingStopsCount)
                                .thenComparingInt(elevator -> elevator.distanceFrom(request.getSourceFloor())))
                        .orElseThrow());
    }

    private boolean allElevatorsIdle() {
        return building.getElevators().stream().allMatch(Elevator::isIdle);
    }
}
