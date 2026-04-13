package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(Item itemDt);

    Item update(Item itemDto);

    List<Item> findAllByUserId(Long userid);

    List<Item> findAllByName(String name);

    Optional<Item> findById(Long id);
}
