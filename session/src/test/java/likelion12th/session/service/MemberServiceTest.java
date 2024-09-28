package likelion12th.session.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void getMembersByPage() {
    }

    @Test
    void printMembersByPage() {
        memberService.printMembersByPage(0, 10);
    }
}