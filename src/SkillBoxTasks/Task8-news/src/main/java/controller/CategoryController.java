package controller;

import model.Category;
import service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        ResponseEntity<Category> categoryResponseEntity = categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        return categoryResponseEntity;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        ResponseEntity<Category> body = ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
        return body;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        ResponseEntity<Category> ok = ResponseEntity.ok(categoryService.updateCategory(id, category));
        return ok;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ResponseEntity<Void> build = ResponseEntity.noContent().build();
        return build;
    }
}
