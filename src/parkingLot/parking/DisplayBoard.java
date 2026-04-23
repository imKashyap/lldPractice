package parkingLot.parking;

public class DisplayBoard implements Observer {

    @Override
    public void update(ParkingFloor floor) {
        System.out.println("[DisplayBoard] Updated status for floor: " + floor.getId() );
        int totalBikes = 0, totalCars =0, totalTrucks = 0, totalEVs = 0, vacantBikes =0, vacantCars = 0, vacantTrucks = 0, vacantEVs = 0;
        for(ParkingSpot spot: floor.getSpots()){
            switch (spot.getType()){
                case BIKE -> {
                    totalBikes++;
                    if (spot.getState() == SpotState.AVAILABLE)
                        vacantBikes++;
                }
                case CAR -> {
                    totalCars++;
                    if (spot.getState() == SpotState.AVAILABLE)
                        vacantCars++;
                }
                case TRUCK -> {
                    totalTrucks++;
                    if (spot.getState() == SpotState.AVAILABLE)
                        vacantTrucks++;
                }
                case EV -> {
                    totalEVs++;
                    if (spot.getState() == SpotState.AVAILABLE)
                        vacantEVs++;
                }

            }
        }

        System.out.println("Vacant Bike Spots: "+ vacantBikes +"/"+totalBikes);
        System.out.println("Vacant Car Spots: "+ vacantCars +"/"+totalCars);
        System.out.println("Vacant Truck Spots: "+ vacantTrucks +"/"+totalTrucks);
        System.out.println("Vacant EV Spots: " + vacantEVs + "/" + totalEVs);
    }
}
