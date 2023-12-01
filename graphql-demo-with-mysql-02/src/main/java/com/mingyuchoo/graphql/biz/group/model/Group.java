package com.mingyuchoo.graphql.biz.group.model;

import com.mingyuchoo.graphql.biz.account.model.Account;
import com.mingyuchoo.graphql.biz.policy.model.Policy;
import com.mingyuchoo.graphql.biz.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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
    private Set<User> user;

    @ManyToMany
    @JoinTable
    private Set<Policy> policy;
}
