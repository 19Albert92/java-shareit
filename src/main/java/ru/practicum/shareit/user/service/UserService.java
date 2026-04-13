package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(CreateUserDto userDto);

    UserDto updateUser(UpdateUserDto userDto, Long userid);

    void deleteUser(Long userid);

    List<UserDto> findAllUsers();

    UserDto findUserById(Long userid);
}
