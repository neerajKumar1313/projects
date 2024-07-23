package com.example.fabricshoppingcart.repository;

import com.example.fabricshoppingcart.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
