package likelion12th.session.service;

import likelion12th.session.domain.*;
import likelion12th.session.dto.request.ArticleCreateRequestDto;
import likelion12th.session.dto.request.ArticleUpdateRequestDto;
import likelion12th.session.dto.response.ArticleResponseDto;
import likelion12th.session.exception.CustomException;
import likelion12th.session.exception.ErrorCode;
import likelion12th.session.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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
                        .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

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

    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleUpdateRequestDto requestDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        //기존 article의 title, content 수정
        if (requestDto.getTitle() != null) {
            article.updateTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null) {
            article.updateContent(requestDto.getContent());
        }

        //기존 article의 category 삭제
        categoryArticleRepository.deleteByArticle(article);
        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                //카테고리 ID를 잘못 입력한 경우 예외처리
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .article(article)
                        .category(category)
                        .build();
                categoryArticleRepository.save(categoryArticle);
            }
        }

        articleRepository.save(article);

        ArticleLog articleLog = ArticleLog.builder()
                .article(article)
                .title(article.getTitle())
                .content(article.getContent())
                .build();
        articleLogRepository.save(articleLog);

        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent());
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        
        articleRepository.deleteById(article.getId());
    }
}
