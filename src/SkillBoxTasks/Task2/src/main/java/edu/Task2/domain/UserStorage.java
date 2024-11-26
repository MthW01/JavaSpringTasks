package edu.Task2.domain;

import edu.Task2.domain.User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserStorage {
    private final Map<Long, User> userMap;

    public UserStorage() {
        this.userMap = new LinkedHashMap<>();
    }

    public UserStorage(List<User> userList) {
        this.userMap = new LinkedHashMap<>();
        userList.forEach(this::addUser);
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
    }

    public void removeUser(Long id) {
        userMap.remove(id);
    }

    public void clearUsers() {
        userMap.clear();
    }

    public List<User> getAllUsers() {
        return List.copyOf(userMap.values());
    }
}
