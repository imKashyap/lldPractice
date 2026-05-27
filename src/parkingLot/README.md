# Parking Lot

### Requirements:
1. The parking lot should have multiple levels, each level with a certain number of parking spots.
2. The parking lot should support different types of vehicles, such as cars, motorcycles, and trucks.
3. Each parking spot should be able to accommodate a specific type of vehicle.
4. The system should assign a parking spot to a vehicle upon entry and release it when the vehicle exits.
5. The system should track the availability of parking spots and provide real-time information to customers. (Available → Occupied).
6. The system should handle multiple entry and exit points and support concurrent access.
7. Fee calculation strategies (weekday vs. weekend, short-term vs. long-term).
----

### ✅ Key Components in the Design

**1. Entities / Classes**

| Class                    | Description                                                                  |
| ------------------------ | ---------------------------------------------------------------------------- |
| `ParkingLot`             | Main class managing all levels and entry/exit points. Singleton.             |
| `Level`                  | Contains multiple `ParkingSpots`. Tracks availability.                       |
| `ParkingSpot`            | Represents a parking space. Associated with a `VehicleType` and `SpotState`. |
| `Vehicle`                | Abstract class with subtypes `Car`, `Bike`, `Truck`.                         |
| `ParkingTicket`          | Issued when a vehicle enters. Stores entry time, spot, etc.                  |
| `DisplayBoard`           | Shows available spot info per level. Uses Observer pattern.                  |
| `EntryGate` / `ExitGate` | Handles entry/exit flows.                                                    |
| `FeeCalculator`          | Strategy pattern to support multiple pricing models.                         |

**2. Design Patterns Used**

| Pattern                | Where it's used                                      | Why                                               |
| ---------------------- | ---------------------------------------------------- | ------------------------------------------------- |
| **Singleton**          | `ParkingLot`                                         | One central instance to coordinate the system.    |
| **Factory**            | `VehicleFactory`, `SpotFactory`                      | To instantiate vehicles and spot types cleanly.   |
| **Strategy**           | `FeeCalculator`                                      | Flexible fee strategies (weekday, weekend, etc.). |
| **Observer**           | `DisplayBoard` observing `Level` or `ParkingSpot`    | Real-time updates for availability.               |
| **Command (optional)** | Entry/exit commands                                  | If using command queue for concurrent handling.   |
| **Template Method**    | Abstract `FeeCalculator` class                       | Shared logic with pluggable pricing rules.        |


**3. Concurrency Handling**
* Use ConcurrentHashMap / ReadWriteLock for concurrent read/writes of spot availability.
* Thread-safe handling of vehicle entry/exit via synchronized or lock mechanisms.
* Spot assignment should be atomic — to avoid double-booking under concurrent load.

## Prep / Design Notes

`ParkingLot`
- Singleton coordinator for the complete parking system.
- Owns entry gates, exit gates, and parking floors.
- Operations: `parkVehicle(Vehicle): ParkingTicket`, `unparkVehicle(Vehicle)`.

`EntryGate`
- Handles vehicle entry.
- Operation: `enter(Vehicle): ParkingTicket`.

`ExitGate`
- Handles vehicle exit.
- Coordinates fee calculation and payment.
- Operation: `exit(ParkingTicket): ParkingReceipt`.

`ParkingFloor`
- Owns parking spots.
- Uses locking around spot assignment and release.
- Operations: `parkAtSpot(Vehicle)`, `unpark(ParkingTicket)`.

`ParkingSpot`
- Has a vehicle-compatible spot type and a spot state.
- Operations: `parkAtSpot(Vehicle)`, `unpark(ParkingTicket)`.

`Vehicle`
- Stores vehicle number and vehicle type.

`ParkingTicket`
- Stores parking floor, parking spot, entry time, and vehicle.

Design flow:
1. Entry gate receives a vehicle.
2. Parking lot finds a suitable floor and spot.
3. Spot is marked occupied and a ticket is issued.
4. Exit gate receives the ticket.
5. Fee strategy calculates amount.
6. Payment strategy collects payment.
7. Spot is released and receipt is generated.
