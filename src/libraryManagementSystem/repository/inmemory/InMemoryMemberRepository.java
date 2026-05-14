package libraryManagementSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.repository.MemberRepository;

public class InMemoryMemberRepository implements MemberRepository {
    private final Map<String, Member> membersById = new ConcurrentHashMap<>();

    @Override
    public void save(Member member) {
        membersById.put(member.getAccount().getUsername(), member);
    }

    @Override
    public Optional<Member> findById(String memberId) {
        return Optional.ofNullable(membersById.get(memberId));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(membersById.values());
    }

    @Override
    public void deleteById(String memberId) {
        membersById.remove(memberId);
    }
}
