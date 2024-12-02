package SkillBoxTasks.Task4.src.main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.repos.NewsCategoryRepository;
import SkillBoxTasks.Task4.src.main.dto.rs.CategoryResponse;
import SkillBoxTasks.Task4.src.main.host.NewsCategoryEntity;
import SkillBoxTasks.Task4.src.main.mapper.CategoryMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsCategoryService {
    private final NewsCategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAll(Pageable pageable) {
        List<NewsCategoryEntity> categoryList = categoryRepository.findAll(pageable).getContent();
        List<CategoryResponse> categoryNames = new ArrayList<>();
        for (NewsCategoryEntity category : categoryList) {
            categoryNames.add(categoryMapper.categoryToResponse(category));
        }
        return categoryNames;
    }

    public CategoryResponse add(String name) {
        NewsCategoryEntity category = new NewsCategoryEntity(name);
        categoryRepository.save(category);
        return categoryMapper.categoryToResponse(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
