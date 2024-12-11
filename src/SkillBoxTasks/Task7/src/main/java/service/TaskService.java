package service;

import lombok.RequiredArgsConstructor;
import domain.Task;
import dto.DTO;
import repository.TaskRepository;
import repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper; // MapStruct mapper

    public Flux<Task> findAllWithUsers() {
        return taskRepository.findAll();
    }

    public Mono<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public Mono<Task> create(DTO taskDTO) {
        Task task;
        task = taskMapper.toTask(taskDTO,userRepository);
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }

    public Mono<Object> update(String id, DTO taskDTO) {
        return Mono.error(new RuntimeException("Task not found"))
            .switchIfEmpty(taskRepository.findById(id)
                .flatMap(task -> {
                    task.setName(taskDTO.name());
                    task.setDescription(taskDTO.description());
                    task.setStatus(taskDTO.status());
                    task.setAssigneeId(taskDTO.assigneeId());
                    task.setUpdatedAt(Instant.now());
                    return taskRepository.save(task);
                }));
    }


    public Mono<Object> addObserver(String taskId, String observerId) {
        return Mono.error(new RuntimeException("Task not found"))
            .switchIfEmpty(taskRepository.findById(taskId)
                .flatMap(task -> {
                    task.getObserverIds().add(observerId);
                    return taskRepository.save(task);
                }));
    }

    public Mono<Boolean> deleteById(String id) {
        return taskRepository.deleteById(id)
                .thenReturn(true)
                .onErrorResume(e -> Mono.just(false));
    }
}
