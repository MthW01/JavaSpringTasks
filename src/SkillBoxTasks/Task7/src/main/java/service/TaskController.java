package service;

import domain.Task;
import dto.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.findAllWithUsers();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task>> getById(@PathVariable String id) {
        return taskService.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Task>> create(@RequestBody DTO DTO) {
        Mono<ResponseEntity<Task>> responseEntityMono = taskService.create(DTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        return responseEntityMono;
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable String id, @RequestBody DTO DTO) {
        Mono<ResponseEntity<Object>> responseEntityMono = taskService.update(id, DTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        return responseEntityMono;
    }

    @PutMapping("/{taskId}/observers/{observerId}")
    public Mono<ResponseEntity<Object>> addObserver(@PathVariable String taskId, @PathVariable String observerId) {
        Mono<ResponseEntity<Object>> responseEntityMono = taskService.addObserver(taskId, observerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        return responseEntityMono;
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        Mono<ResponseEntity<Object>> responseEntityMono = taskService.deleteById(id)
                .map(success -> success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
        return responseEntityMono;
    }
}
