package likelion12th.session.service;

import likelion12th.session.domain.Member;
import likelion12th.session.dto.request.JoinRequest;
import likelion12th.session.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 인코더 DI

    public Page<Member> getMembersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberRepository.findAll(pageable);
    }

    public Page<Member> getMembersOverAgeByPage(int page, int size, int age) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberRepository.findByAgeGreaterThanEqual(age, pageable);
    }

    public Page<Member> getMembersUsernameStartsWithByPage(int page, int size, String start) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("age").ascending());
        return memberRepository.findByUsernameStartsWith(start, pageable);
    }

    public void printMembersByPage(int page, int size) {
        Page<Member> memberPage = getMembersByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername());
        }
    }

    public void printMembersOverAgeByPage(int page, int size, int age) {
        Page<Member> memberPage = getMembersOverAgeByPage(page, size, age);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername() +
                    ", age: " + member.getAge());
        }
    }

    public void printMembersUsernameStartsWithByPage(int page, int size, String start) {
        Page<Member> memberPage = getMembersUsernameStartsWithByPage(page, size, start);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername() +
                    ", age: " + member.getAge());
        }
    }

    public void join(JoinRequest joinRequest) {
        if (memberRepository.existsByUsername(joinRequest.getUsername())) {
            return; // 나중에는 예외 처리
        }

        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequest.getPassword()))
                .build();

        memberRepository.save(member);
    }
}
