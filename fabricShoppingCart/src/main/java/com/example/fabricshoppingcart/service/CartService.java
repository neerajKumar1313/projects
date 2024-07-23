package com.example.fabricshoppingcart.service;

import com.example.fabricshoppingcart.exception.InvalidInput;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;

public interface CartService {

    Cart createCart(Long userId);

    Item addItemToCart(Long cartId, Item item) throws InvalidInput, RuntimeException;

    Item updateItemInCart(Long cartId, Item updatedItem) throws InvalidInput, RuntimeException;

    void removeItemFromCart(Long cartId, Long itemId) throws InvalidInput, RuntimeException;

    double getCartTotal(Long cartId) throws InvalidInput, RuntimeException;

    Cart getCartById(Long cartId) throws InvalidInput, RuntimeException;
}
