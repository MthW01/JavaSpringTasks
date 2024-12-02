package SkillBoxTasks.Task4.src.main.webAdapter;

import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.dto.rq.CommentRequest;
import SkillBoxTasks.Task4.src.main.dto.rs.CommentResponse;
import SkillBoxTasks.Task4.src.main.requires.CommentRequired;
import SkillBoxTasks.Task4.src.main.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment/")
@RequiredArgsConstructor
public class CommentController {
    CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest dto) {
        return ResponseEntity.ok(commentService.add(dto));
    }

    @CommentRequired
    @PutMapping("/{userId}/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long userId,
                                                         @RequestBody String comment,
                                                         @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.update(commentId, userId, comment));
    }

    @CommentRequired
    @DeleteMapping("/{userId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.delete(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
