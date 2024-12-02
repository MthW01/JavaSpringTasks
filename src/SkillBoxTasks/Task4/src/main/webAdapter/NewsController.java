package SkillBoxTasks.Task4.src.main.webAdapter;

import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.dto.rq.NewsRequest;
import SkillBoxTasks.Task4.src.main.dto.rs.NewsFullResponse;
import SkillBoxTasks.Task4.src.main.dto.rs.NewsSmallResponse;
import SkillBoxTasks.Task4.src.main.requires.NewsRequired;
import SkillBoxTasks.Task4.src.main.service.NewsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news/")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsSmallResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(newsService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsFullResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<NewsFullResponse> add(@RequestBody NewsRequest dto) {
        return ResponseEntity.ok(newsService.add(dto));
    }

    @NewsRequired
    @PutMapping("/{authorId}/{newsId}")
    public ResponseEntity<NewsFullResponse> update(@PathVariable Long authorId, @PathVariable Long newsId, @RequestBody String updatedContent) {
        return ResponseEntity.ok(newsService.update(newsId, authorId, updatedContent));
    }

    @NewsRequired
    @DeleteMapping("/{authorId}/{newsId}")
    public ResponseEntity<Void> delete(@PathVariable Long authorId, @PathVariable Long newsId) {
        newsService.delete(newsId, authorId);
        return ResponseEntity.noContent().build();
    }
}
