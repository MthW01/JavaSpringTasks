package service;

import lombok.RequiredArgsConstructor;
import domain.User;
import repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    public Mono<User> update(String id, User updatedUser) {
        Mono<User> userNotFound = userRepository.findById(id)
                .flatMap(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
        return userNotFound;
    }

    public Mono<Boolean> deleteById(String id) {
        Mono<Boolean> booleanMono = userRepository.deleteById(id)
                .thenReturn(true)
                .onErrorResume(e -> Mono.just(false));
        return booleanMono;
    }
}
