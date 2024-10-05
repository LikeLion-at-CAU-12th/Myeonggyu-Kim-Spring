package likelion12th.session.repository;

import likelion12th.session.domain.Article;
import likelion12th.session.domain.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorArticleJpaRepository extends JpaRepository<CategoryArticle, Long> {
    List<CategoryArticle> findByArticle(Article article);
}
