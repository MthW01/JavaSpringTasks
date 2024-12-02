package SkillBoxTasks.Task4.src.main.mapper;

import SkillBoxTasks.Task4.src.main.dto.rs.CommentResponse;
import SkillBoxTasks.Task4.src.main.host.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentResponse commentToResponse(CommentEntity comment){
        return new CommentResponse(
                comment.getContent(),
                comment.getUserEntity().getName(),
                comment.getNewsEntity().getId()
        );
    }
}
