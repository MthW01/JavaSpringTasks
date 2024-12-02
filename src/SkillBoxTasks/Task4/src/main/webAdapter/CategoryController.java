package SkillBoxTasks.Task4.src.main.webAdapter;

import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.dto.rs.CategoryResponse;
import SkillBoxTasks.Task4.src.main.service.NewsCategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category/")
@RequiredArgsConstructor
public class CategoryController {
    private final NewsCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(Pageable pageable) {
        List<CategoryResponse> categories = categoryService.getAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/{name}")
    public ResponseEntity<CategoryResponse> add(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.add(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
