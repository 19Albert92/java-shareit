package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.comment.CommentDto;
import ru.practicum.shareit.item.dto.comment.CreateCommentDto;
import ru.practicum.shareit.item.dto.item.CreateItemDto;
import ru.practicum.shareit.item.dto.item.ItemDto;
import ru.practicum.shareit.item.dto.item.ItemToOwnerDto;
import ru.practicum.shareit.item.dto.item.UpdateItemDto;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemBookingService;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    private final ItemBookingService itemBookingService;

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(
            @RequestBody @Valid CreateItemDto itemDto,
            @Positive(message = "Id должно быть больше 0")
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestBody UpdateItemDto itemDto,
            @Positive(message = "Id рользователя должно быть больше 0")
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Positive(message = "Id должно быть больше 0") @PathVariable Long itemId
    ) {
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemToOwnerDto findItemById(
            @Positive(message = "Id должно быть больше 0")
            @PathVariable Long itemId
    ) {
        return itemBookingService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchActualItems(
            @RequestParam(required = false, defaultValue = "") String text
    ) {
        return itemService.searchItemsByName(text);
    }

    @GetMapping
    public List<ItemToOwnerDto> findItemsByUserId(
            @Positive(message = "Id рользователя должно быть больше 0")
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemBookingService.findItemsWithAfterAndBeforeBookingDateByUserId(userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(
            @Positive(message = "Id должно быть больше 0")
            @PathVariable Long itemId,
            @Positive(message = "Id рользователя должно быть больше 0")
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Valid @RequestBody CreateCommentDto commentDto
    ) {
        return commentService.addComment(itemId, userId, commentDto);
    }
}
