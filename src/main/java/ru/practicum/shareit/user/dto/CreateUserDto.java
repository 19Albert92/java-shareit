package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotNull(message = "Поле 'name' обязательное")
    @NotEmpty(message = "Поле 'name' не должно быть пустым")
    private String name;

    @NotNull(message = "Поле 'email' обязательное")
    @NotEmpty(message = "Поле 'email' не должно быть пустым")
    @Email(message = "Не валидный email")
    private String email;
}
