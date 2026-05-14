package hotelManagementSystem.models.room;

public class Room {
    private final String roomId;
    private final RoomData roomData;
    private volatile RoomStatus status;

    public Room(String roomId, RoomData roomData) {
        this.roomId = roomId;
        this.roomData = roomData;
        this.status = RoomStatus.AVAILABLE;
    }

    public String getRoomId() {
        return roomId;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public synchronized RoomStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room [roomId=" + roomId + ", type=" + roomData.getType() + ", status=" + status + "]";
    }

}
