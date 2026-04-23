
ParkingLot (Singleton)
    - Entry Gate
    - Exit Gate
    - Parking Floors

    - parkVehicle(Vehicle): Ticket
    - unparkVehicle(Vehicle)

EntryGate
    - enter(Vehicle): Ticket

ExitGate
    - CostComputation
        - PricingStrategy
        - PaymentStrategy
    - exit(Ticket): ParkingReceipt


ParkingLevel
    - Parking Spots
    - Lock
    - Parking Strategy

    - parkAtSpot(Vehicle): Level + Spot
    - unpark(Ticket): boolean

ParkingSpot
    - Spot Type
    - Spot State

    parkAtSpot(Vehicle): Spot
    unpark(Ticket): boolean

Vehicle
    - Vehicle Number
    - Vehicle Type

Ticket
    - Parking Level
    - Parking Spot
    - Entry Time
    - Vehicle
