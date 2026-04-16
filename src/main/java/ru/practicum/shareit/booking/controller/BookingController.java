package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto saveBooking(
            @Valid @RequestBody CreateBookingDto bookingDto,
            @Positive(message = "Id должно быть больше 0") @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateApproveBooking(
            @Positive @PathVariable("bookingId") Long id,
            @RequestParam(defaultValue = "false") boolean approved,
            @Positive(message = "Id должно быть больше 0") @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.updateBookingApproved(id, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(
            @Positive(message = "Id должно быть больше 0") @PathVariable("bookingId") Long id,
            @Positive(message = "Id должно быть больше 0") @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.findBookingById(id, userId);
    }

    @GetMapping
    public List<BookingDto> getBookingsByUserId(
            @Positive(message = "Id должно быть больше 0")
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state
    ) {
        System.out.println(state + " <------- test");
        return bookingService.findBookingByState(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsByOwner(
            @Positive(message = "Id должно быть больше 0")
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state
    ) {
        return bookingService.findBookingByOwnerWithState(userId, state);
    }
}
