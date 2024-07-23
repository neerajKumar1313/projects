package com.example.fabricshoppingcart.Controller;

import com.example.fabricshoppingcart.exception.InvalidInputException;
import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;
import com.example.fabricshoppingcart.response.ApiResponse;
import com.example.fabricshoppingcart.service.CartService;
import com.example.fabricshoppingcart.util.Util;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartServiceImpl;

    @PostMapping
    public ResponseEntity<ApiResponse> createCart(@NonNull @RequestParam Long userId) {
        log.info("CartController : request for create cart for userId : " + userId);
        try {
            Cart cartCreated = cartServiceImpl.createCart(userId);
            return Util.createResponse(cartCreated, "success", 200);
        } catch (Exception e) {
            return Util.createResponse(e.getMessage(), "failure", 400);
        }
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse> addItemToCart(@NonNull @PathVariable Long cartId, @NonNull @RequestBody Item item) {
        log.info("CartController : request for add item to cart for cartId : " + cartId);

        try {
            Item responseItem = cartServiceImpl.addItemToCart(cartId, item);
            return Util.createResponse(responseItem, "success", 200);
        } catch (InvalidInputException e) {
            return Util.createResponse(e.getMessage(), "failure", 400);
        } catch (RuntimeException e) {
            return Util.createResponse(e.getMessage(), "failure", 500);
        }
    }

    @PutMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse> updateItemInCart(@NonNull @PathVariable Long cartId, @NonNull @RequestBody Item item) {
        log.info("CartController : request for update item to cart for cartId : " + cartId);
        try {
            Item updatedItem = cartServiceImpl.updateItemInCart(cartId, item);
            return Util.createResponse(updatedItem, "success", 200);
        } catch (InvalidInputException e) {
            return Util.createResponse(e.getMessage(), "failure", 400);
        } catch (Exception e) {
            return Util.createResponse(e.getMessage(), "failure", 500);
        }
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        log.info("CartController : request for remove item to cart for cartId : " + cartId);
        try {
            cartServiceImpl.removeItemFromCart(cartId, itemId);
            return Util.createResponse("Item removed", "success", 200);
        } catch (InvalidInputException e) {
            return Util.createResponse(e.getMessage(), "failure", 400);
        } catch (Exception e) {
            return Util.createResponse(e.getMessage(), "failure", 500);
        }
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<ApiResponse> getCartTotal(@PathVariable Long cartId) {
        log.info("CartController : request for get cart total for cartId : " + cartId);
        try {
            double totalPrice = cartServiceImpl.getCartTotal(cartId);
            return Util.createResponse(totalPrice, "success", 200);
        } catch (InvalidInputException e) {
            return Util.createResponse(e.getMessage(), "failure", 400);
        } catch (Exception e) {
            return Util.createResponse(e.getMessage(), "failure", 500);
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCartById(@NonNull @PathVariable Long cartId) throws InvalidInputException {
        log.info("CartController : request for get cart details for cartId : " + cartId);
        try {
            Cart responseCart = cartServiceImpl.getCartById(cartId);
            return Util.createResponse(responseCart, "success", 200);
        } catch (InvalidInputException e) {
            return Util.createResponse(e.getMessage(), "failure", 400);
        } catch (Exception e) {
            return Util.createResponse(e.getMessage(), "failure", 500);
        }
    }
}


//request , response object -> wrapper  ------> for request its pending
//item same id -> handle   -----> pending
//logging -----> done
//test ------> in progress ----> done