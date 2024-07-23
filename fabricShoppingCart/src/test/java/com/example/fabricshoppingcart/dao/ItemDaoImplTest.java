package com.example.fabricshoppingcart.dao;

import com.example.fabricshoppingcart.model.Item;
import com.example.fabricshoppingcart.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class ItemDaoImplTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemDaoImpl itemDao;

    @Test
    void testGetItemById() {
        Item item = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .build();
        Optional<Item> itemOptional = Optional.of(item);
        Mockito.when(itemRepository.findById(1L)).thenReturn(itemOptional);
        Optional<Item> savedItemOptional = itemDao.getItemById(1L);
        assert (savedItemOptional.isPresent());
    }

    @Test
    void testSaveItem() {
        Item item = Item.builder()
                .id(1L)
                .unitPrice(20)
                .name("book")
                .build();
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Item savedItem = itemDao.saveItem(item);
        assert (Objects.equals(savedItem.getId(), item.getId()));
    }

    @Test
    void testRemoveItem() {
        itemDao.removeItem(1L);
    }
}
