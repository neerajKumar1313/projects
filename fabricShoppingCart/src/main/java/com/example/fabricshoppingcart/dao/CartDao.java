package com.example.fabricshoppingcart.dao;

import com.example.fabricshoppingcart.model.Cart;

import java.util.Optional;

public interface CartDao {

    Cart createCart(Cart cart);

    Optional<Cart> getCartById(Long cartId);

    Cart saveCart(Cart cart);

    void removeCart(Long id);
}
