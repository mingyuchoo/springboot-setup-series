package com.mingyuchoo.graphql.biz.permission.model;

import com.mingyuchoo.graphql.biz.policy.model.Policy;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Permission {
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
    private Policy policy;
}
