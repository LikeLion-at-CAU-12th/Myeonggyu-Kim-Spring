package likelion12th.session.service;

import likelion12th.session.domain.Member;
import likelion12th.session.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private final Random random = new Random();

    // 테스트 실행 전, Member 100개 생성하는 코드입니다.
    @BeforeEach
    public void setUp() {
        IntStream.range(0, 100).forEach(i -> {
            String username = "user" + random.nextInt(10000);
            String email = "user" + random.nextInt(10000) + "@example.com";
            int age = random.nextInt(60 - 18 + 1) + 18;
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .build();
            memberJpaRepository.save(member);
        });
    }


    @Test
    void getMembersByPage() {
    }

    @Test
    @Rollback()
    void printMembersByPage() {
        memberService.printMembersByPage(0, 15);
    }

    @Test
    @Rollback()
    void printMembersOverAgeByPage() {
        memberService.printMembersOverAgeByPage(0, 10, 20);
    }

    @Test
    @Rollback()
    void printMembersUsernameStartsWithByPage() {
        memberService.printMembersUsernameStartsWithByPage(0, 10, "user12");
    }
}