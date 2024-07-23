package com.example.fabricshoppingcart.dao;

import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CartDaoImplTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartDaoImpl cartDao;

    @Test
    void testCreateCart() {
        Cart cart = Cart.builder()
                .id(1L).
                userId(1L)
                .build();
        Mockito.when(cartRepository.save(cart)).thenReturn(cart);
        Cart createdCart = cartDao.createCart(cart);
        assert (createdCart.getId() == 1L);
    }

    @Test
    void testGetCartById() {
        Optional<Cart> cartOptional = Optional.of(Cart.builder()
                .id(1L)
                .userId(1L)
                .build());
        Mockito.when(cartRepository.findById(1L)).thenReturn(cartOptional);
        Optional<Cart> savedCartOptional = cartDao.getCartById(1L);
        assert (savedCartOptional.isPresent());
    }

    @Test
    void testSaveCart() {
        Cart cart = Cart.builder()
                .id(1L).
                userId(1L)
                .build();
        Mockito.when(cartRepository.save(cart)).thenReturn(cart);
        Cart savedCart = cartDao.saveCart(cart);
        assert (savedCart.getId() == 1L);
    }

    @Test
    void testRemoveCart() {
        cartDao.removeCart(1L);
    }
}
