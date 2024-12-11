package dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class NewsDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private Long authorId;
    private Long categoryId;
    private Integer commentsCount;
}
