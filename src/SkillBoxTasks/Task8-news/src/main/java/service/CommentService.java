package service;

import dto.CommentDTO;
import model.Comment;
import model.News;
import model.User;
import repository.CommentRepository;
import repository.NewsRepository;
import repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(Comment comment, Long newsId, Long authorId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));
        User author = userRepository.findById(authorId).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setNews(news);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findAllByNewsId(Long newsId) {
        List<Comment> collect = commentRepository.findAll().stream()
                .filter(comment -> comment.getNews().getId().equals(newsId))
                .collect(Collectors.toList());
        return collect;
    }

    public Comment updateComment(Long commentId, CommentDTO commentDTO) {
        var existingComment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        existingComment.setContent(commentDTO.getContent());
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(existingComment);
    }
}
