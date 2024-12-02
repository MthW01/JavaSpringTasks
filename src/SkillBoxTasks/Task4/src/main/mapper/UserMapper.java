package SkillBoxTasks.Task4.src.main.mapper;

import SkillBoxTasks.Task4.src.main.dto.UserDto;
import SkillBoxTasks.Task4.src.main.host.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto userToUserDto(UserEntity userEntity){
        return new UserDto(userEntity.getId(), userEntity.getName());
    }
}
