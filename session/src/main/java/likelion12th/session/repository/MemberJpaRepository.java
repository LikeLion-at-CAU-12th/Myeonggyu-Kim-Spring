package likelion12th.session.repository;

import likelion12th.session.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);

    Member findOneByUsername(String username);

    //나이가 age 이상인 Member 조회
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable);

    //이름이 주어진 값으로 시작하는 Member 조회
    @Query("select m from Member m where m.username like ?1%")
    Page<Member> findByUsernameStartsWith(String start, Pageable pageable);

    //username 중복 검사
    boolean existsByUsername(String username);
}
