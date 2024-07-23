package com.example.fabricshoppingcart.dao;

import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartDaoImpl implements CartDao {

    @Autowired
    private CartRepository cartRepository;

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void removeCart(Long id) {
        cartRepository.deleteById(id);
    }
}
