package likelion12th.session.repository;

import likelion12th.session.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
    List<Article> findByMemberId(Long memberId);
}
