package com.mingyuchoo.pgsql.biz.item.model;

import java.io.Serializable;

import com.mingyuchoo.pgsql.biz.cart.model.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false) // <-- 외래키의 테이블명 + "_" + 외래키의 PK 컬럼명
    private Cart cart; // <-- 부모(One)에 해당하므로 단수(singular)

    public Item() {}

    public Item(String name, int price) {
        super();
        this.name = name;
        this.price = price;
    }

    public Item(String name, int price, Cart cart) {
        super();
        this.name = name;
        this.price = price;
        this.cart = cart;
    }

    @Override
    public String toString() {
        return String.format("Address[id=%d, name=%s]", this.id, this.name);
    }
}
