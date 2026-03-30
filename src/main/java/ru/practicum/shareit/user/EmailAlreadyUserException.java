package ru.practicum.shareit.user;

public class EmailAlreadyUserException extends RuntimeException {

    public EmailAlreadyUserException(String message) {
        super(message);
    }
}
