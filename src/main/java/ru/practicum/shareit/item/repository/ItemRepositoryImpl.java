package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item save(Item item) {

        Long newId = generateId();

        item.setId(newId);

        items.put(newId, item);

        return item;
    }

    @Override
    public Item update(Item item) {

        items.put(item.getId(), item);

        return item;
    }

    @Override
    public List<Item> findAllByUserId(Long userid) {
        return items.values().stream()
                .filter(item -> item.getOwner().equals(userid))
                .toList();
    }

    @Override
    public List<Item> findAllByName(String name) {
        return items.values().stream()
                .filter(searchItemsByNameWithDescription(name))
                .filter(Item::isAvailable)
                .toList();
    }

    private Predicate<Item> searchItemsByNameWithDescription(String name) {
        return item -> item.getName().equalsIgnoreCase(name) ||  item.getDescription().equalsIgnoreCase(name);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    private Long generateId() {
        return items.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }
}
