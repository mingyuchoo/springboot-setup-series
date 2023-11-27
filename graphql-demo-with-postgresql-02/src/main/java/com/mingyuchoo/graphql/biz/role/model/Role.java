package com.mingyuchoo.graphql.biz.role.model;

import com.mingyuchoo.graphql.biz.policy.model.Policy;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable
    private Set<Policy> policy;
}
