package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateItemRequestDto {
    @NotBlank(message = "Описания запроса не должна быть пустой")
    private String description;
}
