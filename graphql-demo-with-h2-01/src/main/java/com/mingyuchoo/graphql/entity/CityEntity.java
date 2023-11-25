package com.mingyuchoo.graphql.entity;

import java.time.OffsetDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@ToString
@Getter
@Setter
@Entity
@Table(name = "cities")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int population;

    @CreationTimestamp private OffsetDateTime createdAt;

    @UpdateTimestamp private OffsetDateTime updatedAt;

    public CityEntity() {}

    public CityEntity(String name, int population) {
        this.name = name;
        this.population = population;
    }
}
