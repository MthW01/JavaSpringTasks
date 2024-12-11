package contoller;

import domain.Task;
import dto.TaskDTO;
import service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MANAGER')")
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.findAllWithUsers();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MANAGER')")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task>> getById(@PathVariable String id) {
        return taskService.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping
    public Mono<ResponseEntity<Task>> create(@RequestBody TaskDTO taskDTO) {
        Mono<ResponseEntity<Task>> objectMono = taskService.create(taskDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        return objectMono;
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Task>> update(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        Mono<ResponseEntity<Task>> objectMono = taskService.update(id, taskDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        return objectMono;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MANAGER')")
    @PutMapping("/{taskId}/observers/{observerId}")
    public Mono<ResponseEntity<Task>> addObserver(@PathVariable String taskId, @PathVariable String observerId) {
        Mono<ResponseEntity<Task>> objectMono = taskService.addObserver(taskId, observerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        return objectMono;
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        Mono<ResponseEntity<Object>> responseEntityMono = taskService.deleteById(id)
                .map(success -> success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
        return responseEntityMono;
    }
}
