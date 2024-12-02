package SkillBoxTasks.Task4.src.main.mapper;

import SkillBoxTasks.Task4.src.main.dto.rs.CategoryResponse;
import SkillBoxTasks.Task4.src.main.host.NewsCategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse categoryToResponse(NewsCategoryEntity category) {
        return new CategoryResponse(category.getId(), category.getCategoryName());
    }
}
