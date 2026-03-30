package ru.practicum.shareit.booking;

import lombok.Data;

import java.time.Instant;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class Booking {
    private Long id;
    private Instant start;
    private Instant end;
    private Long itemId;
    private Long booker;
    private BookingStatus status;
}
