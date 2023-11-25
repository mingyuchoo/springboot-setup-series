package com.mingyuchoo.graphql.entity;

import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@ToString
@Getter
@Setter
@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @CreationTimestamp private OffsetDateTime createdAt;

    @UpdateTimestamp private OffsetDateTime updatedAt;

    @OneToMany
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private List<BookEntity> books;

    public AuthorEntity() {}

    public AuthorEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
