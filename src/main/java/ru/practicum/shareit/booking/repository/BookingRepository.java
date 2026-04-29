package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdOrderByStartDesc(Long id);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long id, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long id, LocalDateTime start);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long id, BookingStatus status);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long id);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long id, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long id, LocalDateTime start);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long id, BookingStatus status);

    boolean existsByItemIdAndStartLessThanAndEndGreaterThan(Long itemId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByStatusNotAndStartLessThanAndItemIdInOrderByStartDesc(
            BookingStatus status, LocalDateTime now, List<Long> itemIds);

    List<Booking> findAllByStatusNotAndStartGreaterThanAndItemIdInOrderByStartAsc(
            BookingStatus status, LocalDateTime now, List<Long> itemIds);

    boolean existsByItemIdAndBookerIdAndStatusAndEndBefore(Long itemId, Long bookerId, BookingStatus status, LocalDateTime endBefore);
}
