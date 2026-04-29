package ru.practicum.shareit.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.NotAvailableItemException;
import ru.practicum.shareit.item.exeption.ItemNotFoundException;
import ru.practicum.shareit.user.exception.EmailAlreadyUserException;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(EmailAlreadyUserException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyUserException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage(), Map.of()));
    }

    @ExceptionHandler({UserNotFoundException.class, ItemNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), Map.of()));
    }

    @ExceptionHandler({NotUserPermissionException.class})
    public ResponseEntity<ErrorResponse> handleUserNotUserPermissionException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(e.getMessage(), Map.of()));
    }

    @ExceptionHandler({ValidationException.class, BookingNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), Map.of()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), Map.of()));
    }

    @ExceptionHandler({NotAvailableItemException.class, InvalidBookingDateException.class})
    public ResponseEntity<ErrorResponse> handleUserAccessViolationException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), Map.of()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidException(HandlerMethodValidationException e) {

        Map<String, String> errorDetails = e.getAllValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() :
                                "Ошибка валидации"
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Ошибка валидации", errorDetails));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errorDetails = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() :
                                "Ошибка валидации",
                        (existing, replacement) -> existing
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Ошибка валидации", errorDetails));
    }
}
