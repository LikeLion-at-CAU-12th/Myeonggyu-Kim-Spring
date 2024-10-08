package likelion12th.session.controller;

import likelion12th.session.dto.request.ArticleCreateRequestDto;
import likelion12th.session.dto.request.ArticleUpdateRequestDto;
import likelion12th.session.dto.response.ArticleResponseDto;
import likelion12th.session.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<Long> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        Long articleId = articleService.createArticle(requestDto);
        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ArticleResponseDto>> getArticlesByMemberId(@PathVariable("memberId") Long memberId) {
        List<ArticleResponseDto> articles = articleService.findArticlesByMemberId(memberId);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @PatchMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable("articleId") Long articleId, @RequestBody ArticleUpdateRequestDto requestDto) {
        ArticleResponseDto article = articleService.updateArticle(articleId, requestDto);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable("articleId") Long articleId) {
        articleService.deleteArticle(articleId);

        return ResponseEntity.noContent().build();
    }
}
