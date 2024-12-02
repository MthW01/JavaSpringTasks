package SkillBoxTasks.Task4.src.main.dto.rs;

import java.util.List;

public record NewsFullResponse(Long id, String content, String authorName, List<String> comments, String categoryName) {
}
