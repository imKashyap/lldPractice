# Elevator System

## Requirements:
1. A building has multiple elevators and multiple floors
2. A user can request an elevator using up/down buttons at each floor
3. These up/down buttons are used to choose the best elevator to serve the request
4. Once elevator is chosen, the floor is added to it's buicket list
5. A user inside the elevator can also press internal button to select destination floor
6. Request generated in a elevator from internal button should be served by same elevator only
7. Elevators should remain idele(sleep) when no requests, and wake up only when a new request arrives.
8. Requests should be in order of direction
    - Going up: visit floors in ascending order
    - Going down: visit floor in descending order
