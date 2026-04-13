package ru.practicum.shareit.item.exeption;

public class NotUserPermissionException extends RuntimeException {
    public NotUserPermissionException(String message) {
        super(message);
    }
}
