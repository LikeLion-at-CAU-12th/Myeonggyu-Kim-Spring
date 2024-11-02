package likelion12th.session.service;

import likelion12th.session.domain.Article;
import likelion12th.session.domain.Comment;
import likelion12th.session.dto.request.CommentCreateRequestDto;
import likelion12th.session.dto.request.CommentUpdateRequestDto;
import likelion12th.session.dto.response.CommentResponseDto;
import likelion12th.session.exception.CustomException;
import likelion12th.session.exception.ErrorCode;
import likelion12th.session.repository.ArticleJpaRepository;
import likelion12th.session.repository.CommentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private ArticleJpaRepository articleRepository;

    @Autowired
    private CommentJpaRepository commentRepository;

    @Transactional
    public Long createComment(Long articleId, CommentCreateRequestDto requestDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .article(article)
                .build();
        commentRepository.save(comment);

        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.deleteById(comment.getId());
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.updateContent(requestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getContent());
    }

    @Transactional
    public List<CommentResponseDto> findCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent()))
                .toList();
    }
}
