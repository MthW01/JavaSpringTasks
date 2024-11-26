package edu.Task2.evlistener;

import SkillBoxTasks.Task2.src.main.java.edu.Task2.evlistener.event.AddUserEvent;
import SkillBoxTasks.Task2.src.main.java.edu.Task2.evlistener.event.DeleteUserEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AllEventListener {
    @Async
    @EventListener
    public void handleAddUserEvent(AddUserEvent addUserEvent) {
        log.info("User - {} added", addUserEvent.user());
    }

    @Async
    @EventListener
    public void handleDeleteUserEvent(DeleteUserEvent deleteUserEvent) {
        log.info("User with id: {} - deleted", deleteUserEvent.id());
    }
}