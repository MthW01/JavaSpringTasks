package SkillBoxTasks.Task4.src.main.host;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;

    @OneToMany(mappedBy = "author")
    List<Article> articles;

    @OneToMany(mappedBy = "author")
    List<Comment> comments;

    public Account(String username) {
        this.username = username;
    }
}
