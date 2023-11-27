package com.mingyuchoo.graphql.policy.model;

import com.mingyuchoo.graphql.permission.model.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany
    @JoinColumn
    private Set<Permission> permission;
}
