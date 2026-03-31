package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.exception.EmailAlreadyUserException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(CreateUserDto userDto) throws EmailAlreadyUserException {
        if (!userRepository.emailExists(userDto.getEmail())) {

            User user = UserMapper.toUser(userDto);

            user = userRepository.save(user);

            log.info("[UserServiceImpl.createUser] пользователь успешно создан");

            return UserMapper.toUserDto(user);
        }

        log.error("[UserServiceImpl.createUser] email уже используется");

        throw new EmailAlreadyUserException("Email уже используется");
    }

    @Override
    public UserDto updateUser(UpdateUserDto userDto, Long userid) throws EmailAlreadyUserException, UserNotFoundException {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден"));

        if (userDto.getEmail() != null && userRepository.emailExists(userDto.getEmail())) {
            throw new EmailAlreadyUserException("Email уже используется");
        }

        user = userRepository.update(UserMapper.toUserWithUpdateFields(user, userDto));

        log.info("[UserServiceImpl.updateUser] пользователь успешно обнавлен");

        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userid) throws UserNotFoundException {
        userRepository.findById(userid)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден"));

        userRepository.deleteById(userid);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto findUserById(Long userid) throws UserNotFoundException {
        return userRepository.findById(userid)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден"));
    }
}
