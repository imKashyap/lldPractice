package elevator;

import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int currentFloor;
    private ElevatorState state;
    private final NavigableSet<Integer> upStops;
    private final NavigableSet<Integer> downStops;

    public Elevator(int id, int initialFloor) {
        this.id = id;
        this.currentFloor = initialFloor;
        this.state = ElevatorState.IDLE;
        this.upStops = new TreeSet<>();
        this.downStops = new TreeSet<>(Collections.reverseOrder());
    }

    public synchronized void addStop(int floor) {
        if (floor > currentFloor) {
            upStops.add(floor);
        } else if (floor < currentFloor) {
            downStops.add(floor);
        } else {
            System.out.println("Elevator " + id + " already at floor " + floor);
        }
        wakeUpIfIdle();
    }

    public synchronized void moveToNextStop() {
        if (state == ElevatorState.IDLE) {
            wakeUpIfIdle();
        }

        if (state == ElevatorState.MOVING_UP) {
            moveUp();
        } else if (state == ElevatorState.MOVING_DOWN) {
            moveDown();
        }
    }

    public synchronized boolean isIdle() {
        return state == ElevatorState.IDLE && upStops.isEmpty() && downStops.isEmpty();
    }

    public synchronized boolean canServeOnCurrentPath(ExternalRequest request) {
        if (state == ElevatorState.IDLE) {
            return true;
        }

        return (request.getDirection() == Direction.UP
                && state == ElevatorState.MOVING_UP
                && request.getSourceFloor() >= currentFloor)
                || (request.getDirection() == Direction.DOWN
                && state == ElevatorState.MOVING_DOWN
                && request.getSourceFloor() <= currentFloor);
    }

    public synchronized int distanceFrom(int floor) {
        return Math.abs(currentFloor - floor);
    }

    public synchronized int pendingStopsCount() {
        return upStops.size() + downStops.size();
    }

    public int getId() {
        return id;
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized ElevatorState getState() {
        return state;
    }

    private void wakeUpIfIdle() {
        if (state != ElevatorState.IDLE) {
            return;
        }

        if (!upStops.isEmpty()) {
            state = ElevatorState.MOVING_UP;
        } else if (!downStops.isEmpty()) {
            state = ElevatorState.MOVING_DOWN;
        }
    }

    private void moveUp() {
        if (upStops.isEmpty()) {
            switchDirectionOrSleep();
            return;
        }

        currentFloor = upStops.pollFirst();
        System.out.println("Elevator " + id + " stopped at floor " + currentFloor);
        switchDirectionOrSleep();
    }

    private void moveDown() {
        if (downStops.isEmpty()) {
            switchDirectionOrSleep();
            return;
        }

        currentFloor = downStops.pollFirst();
        System.out.println("Elevator " + id + " stopped at floor " + currentFloor);
        switchDirectionOrSleep();
    }

    private void switchDirectionOrSleep() {
        if (state == ElevatorState.MOVING_UP && !upStops.isEmpty()) {
            return;
        }
        if (state == ElevatorState.MOVING_DOWN && !downStops.isEmpty()) {
            return;
        }

        if (state == ElevatorState.MOVING_UP && !downStops.isEmpty()) {
            state = ElevatorState.MOVING_DOWN;
        } else if (state == ElevatorState.MOVING_DOWN && !upStops.isEmpty()) {
            state = ElevatorState.MOVING_UP;
        } else {
            state = ElevatorState.IDLE;
        }
    }
}
