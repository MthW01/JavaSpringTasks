package SkillBoxTasks.Task4.src.main.mapper;

import SkillBoxTasks.Task4.src.main.dto.rs.NewsFullResponse;
import SkillBoxTasks.Task4.src.main.dto.rs.NewsSmallResponse;
import SkillBoxTasks.Task4.src.main.host.CommentEntity;
import SkillBoxTasks.Task4.src.main.host.NewsEntity;
import SkillBoxTasks.Task4.src.main.host.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {
    public NewsFullResponse newsToFullResponse(NewsEntity newsEntity){
        List<String> comments = new ArrayList<>();
        for (CommentEntity commentEntity :newsEntity.getCommentEntities()){
            comments.add(commentEntity.getContent());
        }
        return new NewsFullResponse(
                newsEntity.getId(),
                newsEntity.getContent(),
                newsEntity.getUserEntity().getName(),
                comments,
                newsEntity.getNewsCategoryEntity().getCategoryName());
    }

    public NewsSmallResponse newsToSmallResponse(NewsEntity newsEntity){
        return new NewsSmallResponse(
                newsEntity.getId(),
                newsEntity.getContent(),
                newsEntity.getUserEntity().getName(),
                newsEntity.getNewsCategoryEntity().getCategoryName());
    }
}
