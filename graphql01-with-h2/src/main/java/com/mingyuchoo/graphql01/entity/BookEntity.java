package com.mingyuchoo.graphql01.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@ToString
@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private int pageCount;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private AuthorEntity author;

    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    @JoinColumn(name = "BOOK_STORE_ID", referencedColumnName = "ID")
    private BookStoreEntity bookStore;

    public BookEntity() {}

    public BookEntity(String title, int pageCount) {

        this.title = title;
        this.pageCount = pageCount;
    }
    public BookEntity(String title, int pageCount, AuthorEntity author, BookStoreEntity bookStore) {
        this.title = title;
        this.pageCount = pageCount;
        this.author = author;
        this.bookStore = bookStore;
    }
}
