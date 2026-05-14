package hotelManagementSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import hotelManagementSystem.models.room.Room;
import hotelManagementSystem.models.room.RoomStatus;
import hotelManagementSystem.models.room.RoomType;
import hotelManagementSystem.repository.RoomRepository;

public class InMemoryRoomRepository implements RoomRepository {
    private final Map<String, Room> roomsById = new ConcurrentHashMap<>();

    @Override
    public void save(Room room) {
        roomsById.put(room.getRoomId(), room);
    }

    @Override
    public Optional<Room> findById(String roomId) {
        return Optional.ofNullable(roomsById.get(roomId));
    }

    @Override
    public Optional<Room> findAvailableByRoomType(RoomType roomType) {
        return roomsById.values().stream()
                .filter(room -> room.getRoomData().getType() == roomType)
                .filter(room -> room.getStatus() == RoomStatus.AVAILABLE)
                .sorted(Comparator.comparing(Room::getRoomId))
                .findFirst();
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(roomsById.values());
    }
}
