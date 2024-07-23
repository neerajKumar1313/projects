package com.example.fabricshoppingcart.dao;

import com.example.fabricshoppingcart.model.Item;

import java.util.Optional;

public interface ItemDao {

    Optional<Item> getItemById(Long itemId);

    Item saveItem(Item item);

    void removeItem(Long id);
}
