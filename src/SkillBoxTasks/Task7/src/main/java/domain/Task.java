package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@Document("task")
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    String id;
    String name;
    String description;
    Instant createdAt;
    Instant updatedAt;
    TaskStatus status;
    String authorId;
    String assigneeId;
    Set<String> observerIds;

    @Transient
    User author;
    @Transient
    User assignee;
    @Transient
    Set<User> observers;
}
