package com.example.fabricshoppingcart.service;

import com.example.fabricshoppingcart.dao.CartDaoImpl;
import com.example.fabricshoppingcart.dao.ItemDaoImpl;
import com.example.fabricshoppingcart.exception.InvalidInputException;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CartServiceImplTest {

    @Mock
    CartDaoImpl cartDaoImpl;

    @Mock
    ItemDaoImpl itemDaoImpl;

    @InjectMocks
    CartServiceImpl cartService;

    @Test
    void testCreateCart() {
        Cart cart = Cart.builder()
                .id(1L).
                userId(1L)
                .build();
        Mockito.when(cartDaoImpl.saveCart(Mockito.any())).thenReturn(cart);

        Cart savedCart = cartService.createCart(1L);
        assert (savedCart.getId() == 1L);
        assert (savedCart.getUserId() == 1L);
    }

    @Test
    void testAddItemToCart() throws InvalidInputException {
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .build());
        Item item = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .build();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(cartOptional);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);
        Item savedItem = cartService.addItemToCart(1L, item);
        assert (savedItem.getId() == 1L);
    }

    @Test
    void testAddItemToCartException() {
        Optional<Cart> emptyOptional = Optional.empty();
        Item item = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .build();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(emptyOptional);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);

        try {
            Item savedItem = cartService.addItemToCart(1L, item);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Cart not found"));
        }
    }

    @Test
    void testUpdateItemInCart() throws InvalidInputException {
        Item item = Item.builder()
                .itemId(1L)
                .unitPrice(20)
                .name("book")
                .quantity(2)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item);
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .items(items)
                .build());
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(cartOptional);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);
        Item updatedItem = cartService.updateItemInCart(1L, item);
        assert (updatedItem.getItemId() == 1L);
    }

    @Test
    void testUpdateItemInCartException() {
        Item item = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .quantity(2)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item);
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .items(items)
                .build());
        Optional<Cart> emptyOptional = Optional.empty();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(emptyOptional);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);
        try {
            Item updatedItem = cartService.updateItemInCart(1L, item);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Cart/item not found"));
        }
    }

    @Test
    void testRemoveItemFromCart() throws InvalidInputException {
        Item item1 = Item.builder()
                .itemId(1L)
                .unitPrice(20)
                .name("book")
                .quantity(2)
                .build();
        Item item2 = Item.builder()
                .itemId(2L)
                .unitPrice(30)
                .name("map")
                .quantity(2)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .items(items)
                .build());
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(cartOptional);
        cartService.removeItemFromCart(1L, 1L);
    }

    @Test
    void testRemoveItemFromCartException() {
        Optional<Cart> emptyOptional = Optional.empty();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(emptyOptional);
        try {
            cartService.removeItemFromCart(1L, 1L);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Cart not found"));
        }
    }

    @Test
    void testGetCartTotal() throws InvalidInputException {
        Item item1 = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .quantity(2)
                .build();
        Item item2 = Item.builder()
                .id(2L)
                .unitPrice(30)
                .name("map")
                .quantity(2)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .items(items)
                .build());
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(cartOptional);
        double totalPrice = cartService.getCartTotal(1L);
        assert (totalPrice == 100);
    }

    @Test
    void testGetCartTotalException() {
        Optional<Cart> emptyOptional = Optional.empty();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(emptyOptional);
        try {
            double totalPrice = cartService.getCartTotal(1L);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Cart not found"));
        }
    }

    @Test
    void testCartById() throws InvalidInputException {
        Item item1 = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .quantity(2)
                .build();
        Item item2 = Item.builder()
                .id(2L)
                .unitPrice(30)
                .name("map")
                .quantity(2)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .items(items)
                .build());
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(cartOptional);
        Cart fetchedCart = cartService.getCartById(1L);
        assert (fetchedCart.getItems().size() == 2);
    }

    @Test
    void testCartByIdException() {
        Optional<Cart> emptyOptional = Optional.empty();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(emptyOptional);
        try {
            Cart fetchedCart = cartService.getCartById(1L);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Cart not found"));
        }
    }
}
