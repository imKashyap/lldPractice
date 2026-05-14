package elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Building {
    private final int floors;
    private final List<Elevator> elevators;
    private final ElevatorController elevatorController;

    public Building(int floors, int elevatorCount) {
        if (floors <= 0) {
            throw new IllegalArgumentException("floors must be positive");
        }
        if (elevatorCount <= 0) {
            throw new IllegalArgumentException("elevatorCount must be positive");
        }

        this.floors = floors;
        this.elevators = new ArrayList<>();
        for (int elevatorId = 1; elevatorId <= elevatorCount; elevatorId++) {
            elevators.add(new Elevator(elevatorId, 1));
        }
        this.elevatorController = new ElevatorController(this);
    }

    public ElevatorController getElevatorController() {
        return elevatorController;
    }

    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    public Elevator getElevator(int elevatorId) {
        return elevators.stream()
                .filter(elevator -> elevator.getId() == elevatorId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid elevator id: " + elevatorId));
    }

    public void validateFloor(int floor) {
        if (floor < 1 || floor > floors) {
            throw new IllegalArgumentException("Invalid floor: " + floor);
        }
    }
}
