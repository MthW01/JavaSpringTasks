package SkillBoxTasks.Task4.src.main.host;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class UserComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;
    String message;

    @ManyToOne
    User author;
    @ManyToOne
    Article article;

    public UserComment(String message, User author, Article article) {
        this.message = message;
        this.author = author;
        this.article = article;
    }
}