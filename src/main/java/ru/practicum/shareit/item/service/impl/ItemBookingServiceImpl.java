package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.item.ItemToOwnerDto;
import ru.practicum.shareit.item.exeption.ItemNotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemBookingService;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemBookingServiceImpl implements ItemBookingService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    private final BookingRepository bookingRepository;

    private final CommentService commentService;

    private final LocalDateTime now = LocalDateTime.now();

    @Override
    public List<ItemToOwnerDto> findItemsWithAfterAndBeforeBookingDateByUserId(Long userId) {
        userService.findUserEntityByIdOrThrowAnException(userId);

        List<Item> items = itemRepository.findAllByOwnerId(userId);

        if (items.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> itemIds = items.stream().map(Item::getId).toList();

        Map<Long, List<Booking>> lastBooking = bookingRepository
                .findAllByStatusNotAndStartLessThanAndItemIdInOrderByStartDesc(BookingStatus.REJECTED, now, itemIds)
                .stream().collect(Collectors.groupingBy(b -> b.getItem().getId()));

        Map<Long, List<Booking>> firstBooking = bookingRepository
                .findAllByStatusNotAndStartGreaterThanAndItemIdInOrderByStartAsc(BookingStatus.REJECTED, now, itemIds)
                .stream().collect(Collectors.groupingBy(b -> b.getItem().getId()));

        Map<Long, List<Comment>> comments = commentService.findCommentsByItemIds(itemIds).stream()
                .collect(Collectors.groupingBy(comment -> comment.getItem().getId()));

        return items.stream()
                .map(item -> ItemMapper.toItemOwnerDto(
                        item,
                        firstBooking.getOrDefault(item.getId(), List.of()),
                        lastBooking.getOrDefault(item.getId(), List.of()),
                        comments.getOrDefault(item.getId(), List.of())
                ))
                .toList();
    }

    @Override
    public ItemToOwnerDto getItemById(Long id) {

        Optional<Item> item = itemRepository.findById(id);

        if (item.isEmpty()) {
            throw new ItemNotFoundException("Вещь с таким id не найден");
        }

        Long itemId = item.get().getOwner().getId();

        List<Booking> lastBooking = bookingRepository
                .findAllByStatusNotAndStartLessThanAndItemIdInOrderByStartDesc(
                        BookingStatus.REJECTED, now, List.of(itemId));

        List<Booking> firstBooking = bookingRepository
                .findAllByStatusNotAndStartGreaterThanAndItemIdInOrderByStartAsc(
                        BookingStatus.REJECTED, now, List.of(itemId));

        List<Comment> comments = commentService.findCommentsByOwnerId(itemId);

        return ItemMapper.toItemOwnerDto(item.get(), firstBooking, lastBooking, comments);
    }
}
