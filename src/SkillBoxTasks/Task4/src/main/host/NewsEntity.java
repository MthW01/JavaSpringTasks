package SkillBoxTasks.Task4.src.main.host;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String body;

    @ManyToOne
    User author;

    @ManyToOne
    ArticleCategory category;

    @OneToMany(mappedBy = "article")
    List<Comment> comments;

    public Article(String body, User author, ArticleCategory category) {
        this.body = body;
        this.author = author;
        this.category = category;
        this.comments = new ArrayList<>();
    }
}
