package elevator;

public class ElevatorSystemDemo {
    public static void main(String[] args) {
        Building building = new Building(10, 3);
        ElevatorController controller = building.getElevatorController();

        Elevator firstElevator = controller.requestElevator(3, Direction.UP);
        controller.step();
        controller.selectDestination(firstElevator.getId(), 8);

        Elevator secondElevator = controller.requestElevator(7, Direction.DOWN);
        controller.step();
        controller.selectDestination(secondElevator.getId(), 2);

        controller.requestElevator(5, Direction.UP);
        controller.runUntilIdle();
    }
}
