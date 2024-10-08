package likelion12th.session.controller;

import likelion12th.session.dto.request.CommentCreateRequestDto;
import likelion12th.session.dto.request.CommentUpdateRequestDto;
import likelion12th.session.dto.response.CommentResponseDto;
import likelion12th.session.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles/{articleId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Long> createComment(@PathVariable("articleId") Long articleId, @RequestBody CommentCreateRequestDto requestDto) {
        Long commentId = commentService.createComment(articleId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        CommentResponseDto comment = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByArticleId(@PathVariable("articleId") Long articleId) {
        List<CommentResponseDto> comments = commentService.findCommentsByArticleId(articleId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }
}
