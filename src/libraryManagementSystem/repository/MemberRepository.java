package libraryManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import libraryManagementSystem.model.person.Member;

public interface MemberRepository {
    void save(Member member);

    Optional<Member> findById(String memberId);

    List<Member> findAll();

    void deleteById(String memberId);
}
