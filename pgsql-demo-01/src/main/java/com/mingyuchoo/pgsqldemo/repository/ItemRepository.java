package com.mingyuchoo.pgsqldemo.repository;

import com.mingyuchoo.pgsqldemo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Item findById(long id);
    // List<Item> findByName(String name);
    // List<Item> findAll();
}
