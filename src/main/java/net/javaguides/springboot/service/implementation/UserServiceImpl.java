package net.javaguides.springboot.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Convert UserDto into USer JPA Entity
       User user = UserMapper.mapToUser(userDto);
       User savedUser = userRepository.save(user);
         // Convert User JPA entity to UserDto
        UserDto saveduserDto = UserMapper.mapToUserDto(savedUser);
        return saveduserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user: users) {
            UserDto userDto = UserMapper.mapToUserDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
        //return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User updateUser(User user) {
        // get user
        User existingUser = userRepository.findById(user.getId()).get();
        // update user
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        // save user
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
