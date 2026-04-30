package ru.practicum.shareit.user.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    public void should_returnUser_whenExists() {

        Long expectedId = 1L;

        User user = User.builder()
                .id(expectedId)
                .email("example@gmail.ru")
                .name("example name")
                .build();

        when(userRepository.findById(expectedId)).thenReturn(Optional.ofNullable(user));

        UserDto result = userService.findUserById(expectedId);

        assertNotNull(result);

        assertEquals("example name", result.getName());

        verify(userRepository, times(1)).findById(expectedId);
    }

    @Test
    public void should_keepOldEmail_whenOnlyNameChanged() {
        Long expectedId = 1L;
        String expectedName = "New_name";
        String expectedEmail = "example@gmail.ru";

        User user = User.builder()
                .id(expectedId)
                .email(expectedEmail)
                .name("Old_name")
                .build();

        UpdateUserDto patchedUser = new UpdateUserDto();
        patchedUser.setName(expectedName);

        when(userRepository.findById(expectedId)).thenReturn(Optional.ofNullable(user));

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(patchedUser, expectedId);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        AssertionsForClassTypes.assertThat(savedUser)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("email", expectedEmail)
                .hasFieldOrPropertyWithValue("id", expectedId);
    }

    @Test
    public void should_keepOldName_whenEmailChanged() {

        String expectedName = "Old_name";
        String expectedEmail = "new_example@gmail.ru";
        Long expectedId = 1L;

        User user = User.builder()
                .id(expectedId)
                .email(expectedEmail)
                .name(expectedName)
                .build();

        UpdateUserDto patchedUser = new UpdateUserDto();
        patchedUser.setEmail(expectedEmail);

        when(userRepository.findById(expectedId)).thenReturn(Optional.ofNullable(user));

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(patchedUser, expectedId);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        AssertionsForClassTypes.assertThat(savedUser)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("email", expectedEmail)
                .hasFieldOrPropertyWithValue("id", expectedId);
    }

    @Test
    public void should_returnUserNotFoundException_whenUserDoesNotExist() {
        Long expectedId = 100L;

        when(userRepository.findById(expectedId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(expectedId));

        verify(userRepository, never()).deleteById(expectedId);
    }

    @Test
    public  void should_deleteUser_whenUserExists() {
        Long expectedId = 1L;

        User user = User.builder()
                .id(expectedId)
                .email("Test@gmail.ru")
                .name("Test name")
                .build();

        when(userRepository.findById(expectedId)).thenReturn(Optional.ofNullable(user));

        userService.deleteUser(expectedId);

        verify(userRepository, times(1)).deleteById(expectedId);
    }
}
