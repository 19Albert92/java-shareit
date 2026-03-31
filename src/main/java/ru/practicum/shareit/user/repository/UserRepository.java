package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    User update(User user);

    Optional<User> findById(Long userid);

    Collection<User> findAll();

    void deleteById(Long userid);

    boolean emailExists(String email);
}
