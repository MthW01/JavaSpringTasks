package service;

import domain.Task;
import domain.User;
import dto.DTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class TaskMapper {
    public Task toTask(DTO DTO, UserRepository userRepository) {
        if (DTO == null) {
            return null;
        }
        User author;
        author = userRepository.findById(DTO.authorId()).block();
        User assignee;
        assignee = userRepository.findById(DTO.assigneeId()).block();
        Set<User> observers = new HashSet<>();
        if (DTO.observerIds() != null && !DTO.observerIds().isEmpty()) {
            Set<User> set = new HashSet<>();
            for (String id : DTO.observerIds()) {
                Mono<User> byId = userRepository.findById(id);
                if (byId != null) {
                    set.add(byId.block());
                }
            }
            observers = set;
        }
        return new Task(
            DTO.id(),
            DTO.name(),
            DTO.description(),
            DTO.createdAt(),
            DTO.updatedAt(),
            DTO.status(),
            DTO.authorId(),
            DTO.assigneeId(),
            DTO.observerIds(),
            author,
            assignee,
            observers
        );
    }
}