package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {

        Long userid = generateUserId();

        user.setId(userid);

        users.put(userid, user);

        return user;
    }

    @Override
    public User update(User user) {

        users.put(user.getId(), user);

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
        users.remove(userid);
    }

    @Override
    public boolean emailExists(String email) {
        return users.values().stream()
                .noneMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    private Long generateUserId() {
        return users.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }
}
