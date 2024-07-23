package com.example.fabricshoppingcart.service;

import com.example.fabricshoppingcart.dao.CartDaoImpl;
import com.example.fabricshoppingcart.dao.ItemDaoImpl;
import com.example.fabricshoppingcart.exception.InvalidInput;
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
    void testAddItemToCart() throws InvalidInput {
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
        Item item = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .build();
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(null);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);

        try {
            Item savedItem = cartService.addItemToCart(1L, item);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Error in updating Cart"));
        }
    }

    @Test
    void testUpdateItemInCart() throws InvalidInput {
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
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(cartOptional);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);
        Item updatedItem = cartService.updateItemInCart(1L, item);
        assert (updatedItem.getId() == 1L);
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
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(null);
        Mockito.when(itemDaoImpl.saveItem(Mockito.any())).thenReturn(item);
        try {
            Item updatedItem = cartService.updateItemInCart(1L, item);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Error in updating Cart"));
        }
    }

    @Test
    void testRemoveItemFromCart() throws InvalidInput {
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
        cartService.removeItemFromCart(1L, 1L);
    }

    @Test
    void testRemoveItemFromCartException() {
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(null);
        try {
            cartService.removeItemFromCart(1L, 1L);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Error in updating Cart"));
        }
    }

    @Test
    void testGetCartTotal() throws InvalidInput {
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
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(null);
        try {
            double totalPrice = cartService.getCartTotal(1L);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Error in getting cart total"));
        }
    }

    @Test
    void testCartById() throws InvalidInput {
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
        Mockito.when(cartDaoImpl.getCartById(1L)).thenReturn(null);
        try {
            Cart fetchedCart = cartService.getCartById(1L);
        } catch (Exception e) {
            assert (Objects.equals(e.getMessage(), "Error in getting Cart Details"));
        }
    }
}
