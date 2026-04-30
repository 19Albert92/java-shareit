package ru.practicum.shareit.item.dto.item;

import lombok.Data;

@Data
public class UpdateItemDto {
    private String name;
    private String description;
    private Boolean available;
    private Long request;
}
