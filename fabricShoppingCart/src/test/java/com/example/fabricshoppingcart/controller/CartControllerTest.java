package com.example.fabricshoppingcart.controller;

import com.example.fabricshoppingcart.Controller.CartController;
import com.example.fabricshoppingcart.exception.InvalidInputException;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;
import com.example.fabricshoppingcart.response.ApiResponse;
import com.example.fabricshoppingcart.service.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
public class CartControllerTest {

    @Mock
    private CartServiceImpl cartServiceImpl;

    @InjectMocks
    private CartController cartController;


    @Test
    void testCreateCart() {
        Mockito.when(cartServiceImpl.createCart(1L)).thenReturn(Cart.builder().id(1L).userId(1L).build());
        ApiResponse apiResponse = cartController.createCart(1L).getBody();
        assert (Objects.equals(apiResponse.getStatus(), "success"));
        assert (apiResponse.getData() != null);
    }

    @Test
    void testAddItemToCart() throws InvalidInputException {
        Item item = Item.builder()
                .name("book")
                .quantity(2)
                .unitPrice(20)
                .build();
        Mockito.when(cartServiceImpl.addItemToCart(1L, item)).thenReturn(item);
        ApiResponse apiResponse = cartController.addItemToCart(1L, item).getBody();
        assert (Objects.equals(apiResponse.getStatus(), "success"));
        assert (apiResponse.getData() != null);
    }

    @Test
    void testUpdateItemInCart() throws InvalidInputException {
        Item item = Item.builder()
                .id(1L)
                .name("book")
                .unitPrice(5)
                .build();
        Mockito.when(cartServiceImpl.updateItemInCart(1L, item)).thenReturn(item);
        ApiResponse apiResponse = cartController.updateItemInCart(1L, item).getBody();
        assert (Objects.equals(apiResponse.getStatus(), "success"));
        assert (apiResponse.getData() != null);
    }

    @Test
    void testRemoveItemFromCart() throws InvalidInputException {
        ApiResponse apiResponse = cartController.removeItemFromCart(1L, 1L).getBody();
        assert (Objects.equals(apiResponse.getStatus(), "success"));
        assert (apiResponse.getData() != null);
    }

    @Test
    void testGetCartTotal() throws InvalidInputException {
        Mockito.when(cartServiceImpl.getCartTotal(1L)).thenReturn(100.0);
        ApiResponse apiResponse = cartController.getCartTotal(1L).getBody();
        assert (Objects.equals(apiResponse.getStatus(), "success"));
        assert (apiResponse.getData() != null);
    }

    @Test
    void testGetCartById() throws InvalidInputException {
        Cart cart = Cart.builder()
                .userId(1L)
                .id(1L)
                .build();

        Mockito.when(cartServiceImpl.getCartById(1L)).thenReturn(cart);

        ApiResponse apiResponse = cartController.getCartById(1L).getBody();
        assert (Objects.equals(apiResponse.getStatus(), "success"));
        assert (apiResponse.getData() != null);
    }


}
