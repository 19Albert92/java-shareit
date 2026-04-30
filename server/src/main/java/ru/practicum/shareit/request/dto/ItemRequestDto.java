package ru.practicum.shareit.request.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;

    private String description;

    private Long requestorId;

    private LocalDateTime created;
}
