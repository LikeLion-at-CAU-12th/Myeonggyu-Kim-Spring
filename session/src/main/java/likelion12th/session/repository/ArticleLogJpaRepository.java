package likelion12th.session.repository;

import likelion12th.session.domain.Article;
import likelion12th.session.domain.ArticleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleLogJpaRepository extends JpaRepository<ArticleLog, Long> {
    Optional<ArticleLog> findByArticle(Article article);
}
