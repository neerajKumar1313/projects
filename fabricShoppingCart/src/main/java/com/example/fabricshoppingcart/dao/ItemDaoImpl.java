package com.example.fabricshoppingcart.dao;

import com.example.fabricshoppingcart.model.Item;
import com.example.fabricshoppingcart.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<Item> getItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void removeItem(Long id) {
        itemRepository.deleteById(id);
    }
}
