package likelion12th.session.repository;

import likelion12th.session.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository repository;

    @Test
    @Transactional
    @Rollback()
    void findByUsername() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("lion@lion.com")
                .build();

        Member savedMember = repository.save(member);
        Member findMember = repository.findByUsername(savedMember.getUsername()).get(0);

        assertThat(findMember).isNotNull();
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    @Transactional
    @Rollback()
    public void testFindAll() {
        Member member1 = Member.builder()
                .username("lion1")
                .email("lion1@")
                .age(24)
                .build();
        Member member2 = Member.builder()
                .username("lion2")
                .email("lion2@")
                .age(24)
                .build();
        Member member3 = Member.builder()
                .username("lion3")
                .email("lion3@")
                .age(24)
                .build();

        repository.save(member1);
        repository.save(member2);
        repository.save(member3);

        List<Member> result = repository.findAll();

        for (Member member : result) {
            System.out.println(member.getId() + " " + member.getAge() + " " + member.getUsername());
        }

        assertThat(result).hasSize(3);
    }

    @Test
    @Transactional
    @Rollback()
    public void testFindByUserId() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        repository.save(member);

        Member findMember = repository.findById(member.getId()).orElse(null);

        assertThat(findMember).isNotNull();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member);
    }
}