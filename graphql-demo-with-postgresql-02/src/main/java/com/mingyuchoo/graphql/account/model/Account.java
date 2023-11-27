package com.mingyuchoo.graphql.account.model;

import com.mingyuchoo.graphql.group.model.Group;
import com.mingyuchoo.graphql.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @OneToMany
    @JoinColumn
    private Set<Group> group;

    @OneToMany
    @JoinColumn
    private Set<User> user;
}
