package repository;

import model.News;
import org.springframework.data.jpa.domain.Specification;

public class NewsSpecification {

    public static Specification<News> hasCategory(Long categoryId) {
        Specification<News> newsSpecification = (root, query, criteriaBuilder) -> categoryId != null
                ? criteriaBuilder.equal(root.get("category").get("id"), categoryId) : criteriaBuilder.conjunction();
        return newsSpecification;
    }

    public static Specification<News> hasAuthor(Long authorId) {
        Specification<News> newsSpecification = (root, query, criteriaBuilder) -> authorId != null ?
                criteriaBuilder.equal(root.get("author").get("id"), authorId) : criteriaBuilder.conjunction();
        return newsSpecification;
    }

    public static Specification<News> hasId(Long id) {
        return ((root, query, criteriaBuilder) -> id != null ? criteriaBuilder.equal(root.get("id"), id) : criteriaBuilder.conjunction());
    }
}

