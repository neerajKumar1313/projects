package com.example.fabricshoppingcart.service;


import com.example.fabricshoppingcart.dao.CartDaoImpl;
import com.example.fabricshoppingcart.dao.ItemDaoImpl;
import com.example.fabricshoppingcart.exception.InvalidInputException;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDaoImpl cartDaoImpl;

    @Autowired
    private ItemDaoImpl itemDaoImpl;

    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartDaoImpl.saveCart(cart);
    }

    public Item addItemToCart(Long cartId, Item item) throws InvalidInputException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            item.setCart(cart);
            return itemDaoImpl.saveItem(item);
        } else {
            log.info("CartServiceImpl : cart not found(addItem) for cartId: " + cartId);
            throw new InvalidInputException("Cart not found");
        }
    }

    public Item updateItemInCart(Long cartId, Item updatedItem) throws InvalidInputException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        if (cartOptional != null && cartOptional.isPresent()) {
            for (Item item : cartOptional.get().getItems()) {
                if (item.getItemId().longValue() == updatedItem.getItemId().longValue()) {
                    item.setName(updatedItem.getName());
                    item.setUnitPrice(updatedItem.getUnitPrice());
                    item.setQuantity(updatedItem.getQuantity());
                    return itemDaoImpl.saveItem(item);
                }
            }
            log.info("CartServiceImpl : item not found(updateItem) for cartId: " + cartId);
            throw new InvalidInputException("item not found");
        } else {
            log.info("CartServiceImpl : cart not found(updateItem) for cartId: " + cartId);
            throw new InvalidInputException("Cart/item not found");
        }
    }

    public void removeItemFromCart(Long cartId, Long itemId) throws InvalidInputException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        boolean isDeleted = false;
        if (cartOptional.isPresent()) {
            for (Item item : cartOptional.get().getItems()) {
                if (item.getItemId().longValue() == itemId.longValue()) {
                    item.setCart(null);
                    itemDaoImpl.removeItem(item.getId());
                    isDeleted = true;
                }
            }
            if (!isDeleted) {
                log.info("CartServiceImpl : cart not found(removeItem) for cartId: " + cartId);
                throw new InvalidInputException("item not found");
            }
        } else {
            log.info("CartServiceImpl : cart not found(removeItem) for cartId: " + cartId);
            throw new InvalidInputException("Cart not found");
        }
    }

    public double getCartTotal(Long cartId) throws InvalidInputException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            return cart.getItems().stream()
                    .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                    .sum();
        } else {
            log.info("CartServiceImpl : cart not found(cartTotal) for cartId: " + cartId);
            throw new InvalidInputException("Cart not found");
        }
    }

    public Cart getCartById(Long cartId) throws InvalidInputException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        }
        log.info("CartServiceImpl : cart not found(getCart) for cartId: " + cartId);
        throw new InvalidInputException("Cart not found");
    }
}
