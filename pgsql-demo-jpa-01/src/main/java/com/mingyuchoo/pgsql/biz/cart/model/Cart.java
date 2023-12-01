package com.mingyuchoo.pgsql.biz.cart.model;

import java.io.Serializable;
import java.util.List;

import com.mingyuchoo.pgsql.biz.item.model.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private boolean paid;

    @OneToMany(
            mappedBy = "cart",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true) // <-- Item 엔티티에 있는 Cart 엔티티를 외래키로 잡는 필드명
    private List<Item> items; // <-- 자식(Many)에 해당하므로 복수(plural)

    public Cart() {}

    public Cart(String name, boolean paid) {
        super();
        this.name = name;
        this.paid = paid;
    }

    @Override
    public String toString() {
        return String.format("Address[id=%d, name=%s, paid=%b]", this.id, this.name, this.paid);
    }
}
