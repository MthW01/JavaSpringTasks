package edu.Task2.shell;

import edu.Task2.domain.User;
import edu.Task2.domain.UserStorage;
import SkillBoxTasks.Task2.src.main.java.edu.Task2.evlistener.event.AddUserEvent;
import SkillBoxTasks.Task2.src.main.java.edu.Task2.evlistener.event.DeleteUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MainShell {

    private final UserStorage userStorage;
    private final ApplicationEventPublisher eventPublisher;

    @ShellMethod("Retrieve all users")
    public String showAllUsers() {
        var users = userStorage.getAll();
        if (users.isEmpty()) {
            return "The user list is empty.";
        }

        StringBuilder userOutput = new StringBuilder();
        users.forEach(user -> userOutput.append(user).append("\n"));
        return userOutput.toString();
    }

    @ShellMethod("Add a new user")
    public void addUser(String firstName, String lastName, int age) {
        User newUser = new User(firstName, lastName, age);
        userStorage.add(newUser);
        eventPublisher.publishEvent(new AddUserEvent(newUser.toString()));
    }

    @ShellMethod("Delete a user by UID")
    public void removeUserById(Long id) {
        userStorage.delete(id);
        eventPublisher.publishEvent(new DeleteUserEvent(id.toString()));
    }

    @ShellMethod("Clear all users")
    public String clearAllUsers() {
        userStorage.deleteAll();
        return "All users have been removed from storage.";
    }
}
