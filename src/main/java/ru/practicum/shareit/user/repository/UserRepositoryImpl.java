package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    private final Set<String> emails = new HashSet<>();

    @Override
    public User save(User user) {

        Long userid = generateUserId();

        user.setId(userid);

        addUser(user);

        return user;
    }

    @Override
    public User update(User user) {

        addUser(user);

        return user;
    }

    @Override
    public Optional<User> findById(Long userid) {
        return Optional.ofNullable(users.get(userid));
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void deleteById(Long userid) {
        User removedUser = users.remove(userid);

        if (removedUser != null) {
            emails.remove(removedUser.getEmail());
        }
    }

    @Override
    public boolean emailExists(String email) {
        return emails.contains(email);
    }

    private Long generateUserId() {
        return users.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }

    private void addUser(User user) {
        users.put(user.getId(), user);
        emails.add(user.getEmail());
    }
}
