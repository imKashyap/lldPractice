package elevator;

public class InternalRequest {
    private final int elevatorId;
    private final int destinationFloor;

    public InternalRequest(int elevatorId, int destinationFloor) {
        this.elevatorId = elevatorId;
        this.destinationFloor = destinationFloor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
