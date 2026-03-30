package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(
            @RequestBody @Valid CreateItemDto itemDto,
            @Positive @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestBody @Valid UpdateItemDto itemDto,
            @Positive @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable @Positive Long itemId
    ) {
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findItemById(
            @Positive @PathVariable Long itemId
    ) {
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchActualItems(
            @RequestParam(required = false, defaultValue = "") String text
    ) {
        return itemService.searchItemsByName(text);
    }

    @GetMapping
    public List<ItemDto> findItemsByUserId(
            @Positive @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.getItemsByUserid(userId);
    }
}
