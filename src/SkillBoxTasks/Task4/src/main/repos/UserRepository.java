package SkillBoxTasks.Task4.src.main.repos;

import SkillBoxTasks.Task4.src.main.host.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
