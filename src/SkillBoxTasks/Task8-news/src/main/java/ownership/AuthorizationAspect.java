package ownership;

import repository.CommentRepository;
import repository.NewsRepository;
import repository.UserRepository;
import model.Comment;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class AuthorizationAspect {

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    public AuthorizationAspect(HttpServletRequest request, UserRepository userRepository, NewsRepository newsRepository, CommentRepository commentRepository) {
        this.request = request;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Before("@annotation(Task8-news.ownership.RequiresNewsOwnership)")
    public void checkNewsOwnership(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[0];
        Long newsId = (Long) args[1];

        if (!isNewsOwner(userId, newsId)) {
            throw new AccessDeniedException("User is not the owner of this entity");
        }
    }

    private boolean isNewsOwner(Long userId, Long newsId) {
        var news = newsRepository.findById(newsId).orElseThrow(RuntimeException::new);
        return news.getAuthor().getId().equals(userId);
    }

    @Before("@annotation(ownership.RequiresCommentOwnership)")
    public void checkCommentOwnership(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[0];
        Long commentId = (Long) args[1];

        if (!isCommentOwner(userId, commentId)) {
            throw new AccessDeniedException("User is not the owner of this entity");
        }
    }

    private boolean isCommentOwner(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        return comment.getAuthor().getId().equals(userId);
    }
}
