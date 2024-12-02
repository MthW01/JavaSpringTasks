package SkillBoxTasks.Task4.src.main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.repos.CommentRepository;
import SkillBoxTasks.Task4.src.main.repos.NewsRepository;
import SkillBoxTasks.Task4.src.main.repos.UserRepository;
import SkillBoxTasks.Task4.src.main.dto.rq.CommentRequest;
import SkillBoxTasks.Task4.src.main.dto.rs.CommentResponse;
import SkillBoxTasks.Task4.src.main.host.CommentEntity;
import SkillBoxTasks.Task4.src.main.host.NewsEntity;
import SkillBoxTasks.Task4.src.main.host.UserEntity;
import SkillBoxTasks.Task4.src.main.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentResponse add(CommentRequest commentRequest) {
        Optional<UserEntity> user = userRepository.findById(commentRequest.userId());
        Optional<NewsEntity> news = newsRepository.findById(commentRequest.newsId());
        if (user.isEmpty()) {
            throw new RuntimeException("not found user by id");
        }
        if (news.isEmpty()) {
            throw new RuntimeException("not found news by id");
        }

        CommentEntity commentEntity = new CommentEntity(commentRequest.content(), user.get(), news.get());
        commentRepository.save(commentEntity);
        return commentMapper.commentToResponse(commentEntity);
    }

    public CommentResponse getById(Long id){
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if (comment.isEmpty()){
            throw new RuntimeException("not found comment by id");
        }
        return commentMapper.commentToResponse(comment.get());
    }

    public void delete(Long id, Long userId) {
        commentRepository.deleteById(id);
    }

    public CommentResponse update(Long id, Long userId, String newContent) {
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new RuntimeException("not found comment by id");
        }
        CommentEntity updatedComment = comment.get();
        updatedComment.setContent(newContent);
        commentRepository.save(updatedComment);
        return commentMapper.commentToResponse(updatedComment);
    }
}
