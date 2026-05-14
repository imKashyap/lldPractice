package hotelManagementSystem.models.room;

public class RoomFactory {
    public static Room createRoom(String roomId, RoomType roomType) {
        return switch (roomType) {
            case RoomType.STANDARD -> new Room(roomId, new RoomData(RoomType.STANDARD, 2, 5000));
            case RoomType.DELUXE -> new Room(roomId, new RoomData(RoomType.DELUXE, 3, 8000));
            case RoomType.SUITE -> new Room(roomId, new RoomData(RoomType.SUITE, 5, 10000));
        };
    }
}
