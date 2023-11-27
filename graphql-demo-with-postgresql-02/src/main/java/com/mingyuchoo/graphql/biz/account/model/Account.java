package com.mingyuchoo.graphql.biz.account.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    /**
     * 부모에서 OneToMany 를
     * 자식에서 ManyToOne 으로
     */
}
