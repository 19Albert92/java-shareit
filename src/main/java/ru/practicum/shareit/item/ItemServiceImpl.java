package ru.practicum.shareit.item;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    public List<ItemDto> getItemsByUserid(Long userid) {
        userService.findUserById(userid);

        return itemRepository.findAllByUserId(userid).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto getItemById(Long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new ItemNotFoundException("Вещь с таким id не найден"));
    }

    @Override
    public ItemDto createItem(CreateItemDto itemDto, Long userid) {

        userService.findUserById(userid);

        Item item = ItemMapper.toItem(itemDto, userid);

        item = itemRepository.save(item);

        log.info("[ItemServiceImpl.createItem] вещь успешно создана");

        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(UpdateItemDto itemDto, Long userid, Long itemId) {
        userService.findUserById(userid);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Вещь с таким id не найден"));

        item = itemRepository.update(ItemMapper.toItemWithUpdateFields(item, itemDto));

        log.info("[ItemServiceImpl.updateItem] вещь успешно обнавлена");

        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> searchItemsByName(String name) {

        log.debug("[ItemServiceImpl.searchItemsByName] поиск по слову {}",  name);

        if (name.isEmpty()) {
            return Collections.emptyList();
        }

        return itemRepository.findAllByName(name).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
