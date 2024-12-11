package controller;

import ownership.RequiresNewsOwnership;
import dto.CommentDTO;
import model.Comment;
import model.User;
import service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<Comment>> getCommentsByNewsId(@PathVariable Long newsId) {
        return ResponseEntity.ok(commentService.findAllByNewsId(newsId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,
                                                 @RequestParam Long newsId,
                                                 @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment, newsId, user.getId()));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @RequiresNewsOwnership
    @PutMapping("/{userId}/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long userId,
                                                 @RequestBody CommentDTO comment,
                                                 @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.updateComment(commentId, comment));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @RequiresNewsOwnership
    @DeleteMapping("/{userId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
