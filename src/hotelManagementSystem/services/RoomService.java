package hotelManagementSystem.services;

import java.util.List;

import hotelManagementSystem.models.room.Room;
import hotelManagementSystem.models.room.RoomStatus;
import hotelManagementSystem.models.room.RoomType;
import hotelManagementSystem.repository.RoomRepository;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public Room findAvailableRoom(RoomType roomType) {
        return roomRepository.findAvailableByRoomType(roomType)
                .orElseThrow(() -> new IllegalStateException("No available " + roomType + " room found"));
    }

    public void markUnderMaintenance(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));
        room.setStatus(RoomStatus.UNDER_MAINTENANCE);
        roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
