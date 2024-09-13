package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import likelion12th.session.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    void save() {
        Member member = Member.builder()
                .username("memberA")
                .age(24)
                .email("kmgdding9@naver.com")
                .build();

        Long saveId = repository.save(member);
        Member result = repository.findOne(saveId);

        assertThat(member).isEqualTo(result);
        assertThat(member.getId()).isEqualTo(saveId);
    }

    @Test
    @Transactional
    @Rollback()
    void save1() {
        Member member = Member.builder()
                .username("memberA")
                .age(24)
                .email("kmgdding9@naver.com")
                .build();

        Long saveId = repository.save(member);
        em.flush();
        em.clear();
        Member result = repository.findOne(saveId);

        assertThat(saveId).isEqualTo(result.getId());
        assertThat(member).isNotEqualTo(result);
    }

    @Test
    @Transactional
    @Rollback()
    void findAll() {
        Member member1 = Member.builder()
                .username("member1")
                .age(23)
                .email("lion@lion.com")
                .build();

        Member member2 = Member.builder()
                .username("member2")
                .age(23)
                .email("lion@lion.com")
                .build();

        Member member3 = Member.builder()
                .username("member3")
                .age(23)
                .email("lion@lion.com")
                .build();

        repository.save(member1);
        repository.save(member2);
        repository.save(member3);

        em.flush();
        em.clear();

        List<Member> members = repository.findAll();

        for (Member member : members) {
            System.out.println("id: " + member.getId() +
                    " age: " + member.getAge() + "username: " + member.getUsername());
        }
    }

    @Test
    @Transactional
    @Rollback
    void findByUsername() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("lion@lion.com")
                .build();

        Long saveId = repository.save(member);

        em.clear();

        List<Member> result = repository.findByUsername(member.getUsername());

        for (Member mem : result) {
            System.out.println("member = " + mem);
        }
    }
}