package com.example.fabricshoppingcart.ITTest.controller;

import com.example.fabricshoppingcart.Controller.CartController;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;
import com.example.fabricshoppingcart.response.ApiResponse;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

    @Autowired
    CartController cartController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int port;

    @Test
    void testCreateCart() {
        ResponseEntity<Repsonse.ResponseCart> result = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart?userId=1", port), HttpMethod.POST, null, Repsonse.ResponseCart.class);
        assert (Objects.requireNonNull(result.getBody()).getData() != null);
        ApiResponse repsonse = (ApiResponse<Cart>) result.getBody();
        Cart createdCart = (Cart) repsonse.getData();
        assert (createdCart.getUserId() != null);
        assert (createdCart.getId() != null);

    }

    @Test
    void testAddItemToCart() {
        ResponseEntity<Repsonse.ResponseCart> result = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart?userId=2", port), HttpMethod.POST, null, Repsonse.ResponseCart.class);
        ApiResponse repsonse = (ApiResponse<Cart>) result.getBody();
        Cart createdCart = (Cart) repsonse.getData();
        Long cartId = createdCart.getId();
        Item item = Item.builder()
                .id(1L)
                .name("book")
                .quantity(2)
                .unitPrice(20)
                .build();

        HttpEntity<Item> request = new HttpEntity<>(item);
        ResponseEntity<Repsonse.ResponseItem> response = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/items", port, cartId), HttpMethod.POST, request, Repsonse.ResponseItem.class);
        ApiResponse apiResponse = (ApiResponse<Item>) response.getBody();
        Item addedItem = (Item) apiResponse.getData();
        assert (addedItem.getId() == 1);
    }

    @Test
    void testUpdateItemInCart() {
        ResponseEntity<Repsonse.ResponseCart> result = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart?userId=2", port), HttpMethod.POST, null, Repsonse.ResponseCart.class);
        ApiResponse repsonse = (ApiResponse<Cart>) result.getBody();
        Cart createdCart = (Cart) repsonse.getData();
        Long cartId = createdCart.getId();
        Item item = Item.builder()
                .name("book")
                .quantity(2)
                .unitPrice(20)
                .build();

        HttpEntity<Item> request = new HttpEntity<>(item);
        ResponseEntity<Repsonse.ResponseItem> response = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/items", port, cartId), HttpMethod.POST, request, Repsonse.ResponseItem.class);
        ApiResponse apiResponse = (ApiResponse<Item>) response.getBody();
        Item addedItem = (Item) apiResponse.getData();
        assert (addedItem.getId() != null);
        assert (addedItem.getQuantity() == 2);

        item = Item.builder()
                .id(addedItem.getId())
                .name("book")
                .quantity(4)
                .unitPrice(20)
                .build();

        request = new HttpEntity<>(item);
        ResponseEntity<Repsonse.ResponseItem> res = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/items", port, cartId), HttpMethod.PUT, request, Repsonse.ResponseItem.class);
        ApiResponse apiRes = (ApiResponse<Item>) res.getBody();
        Item updatedItem = (Item) apiRes.getData();
        assert (addedItem.getId() == updatedItem.getId());
        assert (updatedItem.getQuantity() == 4);
    }

    @Test
    void testRemoveItemFromCart() {
        ResponseEntity<Repsonse.ResponseCart> result = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart?userId=2", port), HttpMethod.POST, null, Repsonse.ResponseCart.class);
        ApiResponse repsonse = (ApiResponse<Cart>) result.getBody();
        Cart createdCart = (Cart) repsonse.getData();
        Long cartId = createdCart.getId();
        Item item = Item.builder()
                .name("book")
                .quantity(2)
                .unitPrice(20)
                .build();

        HttpEntity<Item> request = new HttpEntity<>(item);
        ResponseEntity<Repsonse.ResponseItem> response = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/items", port, cartId), HttpMethod.POST, request, Repsonse.ResponseItem.class);
        ApiResponse apiResponse = (ApiResponse<Item>) response.getBody();
        Item addedItem = (Item) apiResponse.getData();
        assert (addedItem.getId() != null);
        assert (addedItem.getQuantity() == 2);


        ResponseEntity<Repsonse.ResponseDelete> apiRes = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/items/%s", port, cartId, addedItem.getId()), HttpMethod.DELETE, request, Repsonse.ResponseDelete.class);
        assert (apiRes.getBody() != null);

    }

    @Test
    void testGetCartTotal() {
        ResponseEntity<Repsonse.ResponseCart> result = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart?userId=2", port), HttpMethod.POST, null, Repsonse.ResponseCart.class);
        ApiResponse repsonse = (ApiResponse<Cart>) result.getBody();
        Cart createdCart = (Cart) repsonse.getData();
        Long cartId = createdCart.getId();
        Item item = Item.builder()
                .name("book")
                .quantity(2)
                .unitPrice(20)
                .build();

        HttpEntity<Item> request = new HttpEntity<>(item);
        ResponseEntity<Repsonse.ResponseItem> response = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/items", port, cartId), HttpMethod.POST, request, Repsonse.ResponseItem.class);
        ApiResponse apiResponse = (ApiResponse<Item>) response.getBody();
        Item addedItem = (Item) apiResponse.getData();
        assert (addedItem.getId() != null);
        assert (addedItem.getQuantity() == 2);

        ResponseEntity<Repsonse.ResponseTotalPrice> ApiRes = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s/total", port, cartId), HttpMethod.GET, request, Repsonse.ResponseTotalPrice.class);
        ApiResponse apiResp = (ApiResponse<Double>) ApiRes.getBody();
        double totalPrice = (double) apiResp.getData();
        assert (totalPrice == 40);
    }

    @Test
    void testGetCartById() {
        ResponseEntity<Repsonse.ResponseCart> result = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart?userId=2", port), HttpMethod.POST, null, Repsonse.ResponseCart.class);
        ApiResponse repsonse = (ApiResponse<Cart>) result.getBody();
        Cart createdCart = (Cart) repsonse.getData();
        Long cartId = createdCart.getId();

        ResponseEntity<Repsonse.ResponseCart> apiRes = testRestTemplate.exchange(String.format("http://localhost:%s/api/v1/cart/%s", port, cartId), HttpMethod.GET, null, Repsonse.ResponseCart.class);
        ApiResponse rep = (ApiResponse<Cart>) apiRes.getBody();
        Cart savedCart = (Cart) rep.getData();
        assert (savedCart.getId() == cartId);
    }


}
