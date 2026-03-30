package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateItemDto {

    @NotNull(message = "Поле 'name' обязательное")
    @NotEmpty(message = "Поле 'name' не должно быть пустым")
    private String name;

    @NotNull(message = "Поле 'description' обязательное")
    @NotEmpty(message = "Поле 'description' не должно быть пустым")
    private String description;
    private Boolean available;
}
