package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

public interface RequestService {
    ItemRequestDto createRequest(CreateItemRequestDto itemRequest);
}
