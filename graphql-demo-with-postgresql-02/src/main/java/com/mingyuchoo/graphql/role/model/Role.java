package com.mingyuchoo.graphql.role.model;

import com.mingyuchoo.graphql.policy.model.Policy;
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

    @OneToMany
    @JoinColumn
    private Set<Policy> policy;
}
