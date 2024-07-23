package com.example.fabricshoppingcart.service;


import com.example.fabricshoppingcart.dao.CartDaoImpl;
import com.example.fabricshoppingcart.dao.ItemDaoImpl;
import com.example.fabricshoppingcart.exception.InvalidInput;
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

    public Item addItemToCart(Long cartId, Item item) throws InvalidInput, RuntimeException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        try {
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                item.setCart(cart);
                return itemDaoImpl.saveItem(item);
            } else {
                log.info("CartServiceImpl : cart not found(addItem) for cartId: " + cartId);
                throw new InvalidInput("Cart not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in updating Cart");
        }
    }

    public Item updateItemInCart(Long cartId, Item updatedItem) throws InvalidInput, RuntimeException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        try {
            if (cartOptional.isPresent()) {
                for (Item item : cartOptional.get().getItems()) {
                    if (item.getId().longValue() == updatedItem.getId().longValue()) {
                        item.setName(updatedItem.getName());
                        item.setUnitPrice(updatedItem.getUnitPrice());
                        item.setQuantity(updatedItem.getQuantity());
                        return itemDaoImpl.saveItem(item);
                    } else {
                        log.info("CartServiceImpl : item not found(updateItem) for cartId: " + cartId);
                        throw new InvalidInput("item not found");
                    }
                }
            } else {
                log.info("CartServiceImpl : cart not found(updateItem) for cartId: " + cartId);
                throw new InvalidInput("Cart/item not found");
            }
        } catch (Exception e) {

            log.error("CartServiceImpl : cart not found(updateItem) for cartId: " + cartId);
            throw new RuntimeException("Error in updating Cart");
        }
        return null;
    }

    public void removeItemFromCart(Long cartId, Long itemId) throws InvalidInput, RuntimeException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        try {
            if (cartOptional.isPresent()) {
                for (Item item : cartOptional.get().getItems()) {
                    if (item.getId().longValue() == itemId.longValue()) {
                        item.setCart(null);
                        itemDaoImpl.removeItem(itemId);
                    }
                }
            } else {
                log.info("CartServiceImpl : cart not found(removeItem) for cartId: " + cartId);
                throw new InvalidInput("Cart not found");
            }
        } catch (Exception e) {

            log.error("CartServiceImpl : cart not found(removeItem) for cartId: " + cartId);
            throw new RuntimeException("Error in updating Cart");
        }
    }

    public double getCartTotal(Long cartId) throws InvalidInput, RuntimeException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        try {
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                return cart.getItems().stream()
                        .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                        .sum();
            } else {
                log.info("CartServiceImpl : cart not found(cartTotal) for cartId: " + cartId);
                throw new InvalidInput("Cart not found");
            }
        } catch (Exception e) {
            log.error("CartServiceImpl : cart not found(cartTotal) for cartId: " + cartId);
            throw new RuntimeException("Error in getting cart total");
        }
    }

    public Cart getCartById(Long cartId) throws InvalidInput, RuntimeException {
        Optional<Cart> cartOptional = cartDaoImpl.getCartById(cartId);
        try {
            if (cartOptional.isPresent()) {
                return cartOptional.get();
            }
            log.info("CartServiceImpl : cart not found(getCart) for cartId: " + cartId);
            return null;
        } catch (Exception e) {
            log.error("CartServiceImpl : cart not found(getCart) for cartId: " + cartId);
            throw new RuntimeException("Error in getting Cart Details");
        }
    }
}
