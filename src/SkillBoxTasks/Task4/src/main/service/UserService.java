package SkillBoxTasks.Task4.src.main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.repos.UserRepository;
import SkillBoxTasks.Task4.src.main.dto.UserDto;
import SkillBoxTasks.Task4.src.main.host.UserEntity;
import SkillBoxTasks.Task4.src.main.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAll() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity user : users) {
            userDtoList.add(userMapper.userToUserDto(user));
        }
        return userDtoList;
    }

    public UserDto add(String name) {
        UserEntity user = new UserEntity(name);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public UserDto getById(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new RuntimeException("not found user by id");
        }
        return userMapper.userToUserDto(user.get());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
