package hotelManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import hotelManagementSystem.models.room.Room;
import hotelManagementSystem.models.room.RoomType;

public interface RoomRepository {
    void save(Room room);

    Optional<Room> findById(String roomId);

    Optional<Room> findAvailableByRoomType(RoomType roomType);

    List<Room> findAll();
}
