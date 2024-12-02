package src.main.requires;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import SkillBoxTasks.Task4.src.main.repos.CommentRepository;
import SkillBoxTasks.Task4.src.main.repos.NewsRepository;
import SkillBoxTasks.Task4.src.main.host.CommentEntity;
import SkillBoxTasks.Task4.src.main.host.NewsEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class AuthorizationAspect {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    public AuthorizationAspect(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Before("@annotation(org.example.skillbox_mod4.aspect.NewsRequired)")
    public void checkNewsOwnership(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[0];
        Long newsId = (Long) args[1];
        Optional<NewsEntity>  news = newsRepository.findById(newsId);
        if (news.isEmpty()){
            throw new RuntimeException("not found news by id");
        }
        if (!news.get().getUserEntity().getId().equals(userId)) {
            throw new RuntimeException("User is not the owner of this news");
        }
    }

    @Before("@annotation(org.example.skillbox_mod4.aspect.CommentRequired)")
    public void checkCommentOwnership(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[0];
        Long commentId = (Long) args[1];
        Optional<CommentEntity>  comment = commentRepository.findById(commentId);
        if (comment.isEmpty()){
            throw new RuntimeException("not found comment by id");
        }
        if (!comment.get().getUserEntity().getId().equals(userId)) {
            throw new RuntimeException("User is not the owner of this comment");
        }
    }
}
