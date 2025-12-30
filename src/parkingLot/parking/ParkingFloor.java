package parkingLot.parking;

import parkingLot.vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingFloor {
    private final String id;
    private final List<ParkingSpot> spots;
    private final List<Observer> observers;


    public ParkingFloor(String id) {
        this.id = id;
        this.spots = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addParkingSpot(ParkingSpot spot){
        spots.add(spot);
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(Observer observer:observers){
            observer.update(this);
        }
    }

    public synchronized Optional<ParkingSpot> findAvailableSpot(VehicleType type) {
        return spots.stream()
                .filter(spot -> spot.getState() == SpotState.AVAILABLE && spot.getType() == type)
                .findFirst();
    }

    public String getId() {
        return id;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }
}
