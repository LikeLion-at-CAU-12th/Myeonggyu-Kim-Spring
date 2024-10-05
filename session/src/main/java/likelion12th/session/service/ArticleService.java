package likelion12th.session.service;

import likelion12th.session.domain.*;
import likelion12th.session.dto.request.ArticleCreateRequestDto;
import likelion12th.session.dto.response.ArticleResponseDto;
import likelion12th.session.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ArticleJpaRepository articleRepository;
    @Autowired
    private CategorArticleJpaRepository categoryArticleRepository;
    @Autowired
    private ArticleLogJpaRepository articleLogRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Transactional
    public Long createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                //정석적인 에러 처리 방식은 아님
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .comments(new ArrayList<>())
                .build();
        articleRepository.save(article);

        ArticleLog articleLog = ArticleLog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .article(article)
                .build();
        articleLogRepository.save(articleLog);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 존재하지 않습니다."));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }

        return article.getId();
    }

    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId);
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }
}
