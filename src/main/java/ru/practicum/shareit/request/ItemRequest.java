package ru.practicum.shareit.request;

import lombok.Data;

import java.time.Instant;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequest {
    private Long id;
    private String description;
    private Long requestor;
    private Instant created;
}
