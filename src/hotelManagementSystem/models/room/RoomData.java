package hotelManagementSystem.models.room;

public class RoomData {
    private final RoomType type;
    private final int capacity;
    private final double basePrice;

    public RoomData(RoomType type, int capacity, double basePrice) {
        this.type = type;
        this.capacity = capacity;
        this.basePrice = basePrice;
    }

    public RoomType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getBasePrice() {
        return basePrice;
    }

}
