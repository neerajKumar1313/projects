package com.example.fabricshoppingcart.service;

import com.example.fabricshoppingcart.exception.InvalidInputException;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;

public interface CartService {

    Cart createCart(Long userId);

    Item addItemToCart(Long cartId, Item item) throws InvalidInputException, RuntimeException;

    Item updateItemInCart(Long cartId, Item updatedItem) throws InvalidInputException, RuntimeException;

    void removeItemFromCart(Long cartId, Long itemId) throws InvalidInputException, RuntimeException;

    double getCartTotal(Long cartId) throws InvalidInputException, RuntimeException;

    Cart getCartById(Long cartId) throws InvalidInputException, RuntimeException;
}
