package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateItemDto {

    @NotEmpty(message = "Поле 'name' не должно быть пустым")
    private String name;

    @NotEmpty(message = "Поле 'description' не должно быть пустым")
    private String description;

    @NotNull(message = "Поле 'available' обязательное")
    private Boolean available;
}
