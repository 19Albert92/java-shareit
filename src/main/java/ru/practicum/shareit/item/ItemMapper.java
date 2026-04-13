package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    public static Item toItem(CreateItemDto itemDto, Long userid) {
        return Item.builder()
                .owner(userid)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(userid)
                .build();
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .available(item.isAvailable())
                .name(item.getName())
                .description(item.getDescription())
                .owner(item.getOwner())
                .request(item.getRequest())
                .build();
    }

    public static Item toItemWithUpdateFields(Item item, UpdateItemDto itemDto) {

        if (itemDto.hasAvailable()) {
            item.setAvailable(itemDto.getAvailable());
        }

        if (itemDto.hasName()) {
            item.setName(itemDto.getName());
        }

        if (itemDto.hasDescription()) {
            item.setDescription(itemDto.getDescription());
        }

        return item;
    }
}
