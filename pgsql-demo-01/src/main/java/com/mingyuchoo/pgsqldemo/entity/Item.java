package com.mingyuchoo.pgsqldemo.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item implements Serializable {

    private static final long serialVersionUID = -1427193799783232991L;

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
