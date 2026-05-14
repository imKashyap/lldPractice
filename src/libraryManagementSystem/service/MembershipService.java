package libraryManagementSystem.service;

import java.util.Optional;

import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.model.person.MemberStatus;
import libraryManagementSystem.repository.MemberRepository;

public class MembershipService {
    private final MemberRepository memberRepository;

    public MembershipService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findMember(String memberId) {
        return memberRepository.findById(memberId);
    }

    public void suspendMember(String memberId) {
        memberRepository.findById(memberId).ifPresent(member -> {
            member.setStatus(MemberStatus.SUSPENDED);
            memberRepository.save(member);
        });
    }
}
