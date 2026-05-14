package libraryManagementSystem.repository;

import java.util.List;

import libraryManagementSystem.model.fine.Fine;

public interface FineRepository {
    void save(Fine fine);

    List<Fine> findByMemberId(String memberId);
}
