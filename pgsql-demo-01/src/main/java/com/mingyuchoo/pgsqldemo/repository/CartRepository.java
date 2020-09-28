package com.mingyuchoo.pgsqldemo.repository;

import com.mingyuchoo.pgsqldemo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    // Cart findById(long id);
    // List<Cart> findByName(String name);
    // List<Cart> findAll();
}
