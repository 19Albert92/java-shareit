package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userid}")
    public UserDto getUser(
            @Positive @PathVariable Long userid
    ) {
        return userService.findUserById(userid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/{userid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Positive @PathVariable Long userid) {
        userService.deleteUser(userid);
    }

    @PatchMapping("/{userid}")
    public UserDto updateUser(
            @Positive @PathVariable Long userid,
            @Valid @RequestBody UpdateUserDto userDto
    ) {
        return userService.updateUser(userDto, userid);
    }
}
