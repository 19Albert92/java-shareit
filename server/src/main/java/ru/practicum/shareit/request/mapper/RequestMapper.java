package ru.practicum.shareit.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ItemRequestDto toRequestDto(ItemRequest createRequest) {
        return ItemRequestDto.builder()
                .id(createRequest.getId())
                .description(createRequest.getDescription())
                .requestorId(createRequest.getRequestorId())
                .created(createRequest.getCreatedAt())
                .build();
    }

    public static ItemRequest toItemRequest(CreateItemRequestDto createRequestDto) {
        return ItemRequest.builder()
                .description(createRequestDto.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
