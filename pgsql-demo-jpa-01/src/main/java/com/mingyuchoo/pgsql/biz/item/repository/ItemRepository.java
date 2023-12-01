package com.mingyuchoo.pgsql.biz.item.repository;

import com.mingyuchoo.pgsql.biz.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Item findById(long id);
    // List<Item> findByName(String name);
    // List<Item> findAll();
}
