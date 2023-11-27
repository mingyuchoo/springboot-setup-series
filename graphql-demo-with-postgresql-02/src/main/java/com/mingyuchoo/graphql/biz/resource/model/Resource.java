package com.mingyuchoo.graphql.biz.resource.model;

import com.mingyuchoo.graphql.biz.account.model.Account;
import com.mingyuchoo.graphql.biz.role.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

enum Type {
    Account,
    Service,
    External,
}

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String name;

    /**
     * 부모에서 OneToMany 를
     * 자식에서 ManyToOne 으로
     */
    @ManyToOne
    @JoinColumn
    private Account account;

    @ManyToMany
    @JoinTable
    private Set<Role> role;
}
