package libraryManagementSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import libraryManagementSystem.model.fine.Fine;
import libraryManagementSystem.repository.FineRepository;

public class InMemoryFineRepository implements FineRepository {
    private final Map<String, Fine> finesById = new ConcurrentHashMap<>();

    @Override
    public void save(Fine fine) {
        finesById.put(fine.getFineId(), fine);
    }

    @Override
    public List<Fine> findByMemberId(String memberId) {
        return finesById.values().stream()
                .filter(fine -> fine.getMember().getAccount().getUsername().equals(memberId))
                .toList();
    }
}
