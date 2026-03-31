package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getItemsByUserid(Long userid);

    ItemDto getItemById(Long id);

    ItemDto createItem(CreateItemDto itemDto, Long userid);

    ItemDto updateItem(UpdateItemDto itemDto, Long userid, Long itemId);

    List<ItemDto> searchItemsByName(String name);
}
