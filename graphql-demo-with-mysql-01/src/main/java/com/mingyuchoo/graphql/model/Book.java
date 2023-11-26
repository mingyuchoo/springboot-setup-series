package com.mingyuchoo.graphql.model;

import java.time.OffsetDateTime;
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
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private int pageCount;

    @CreationTimestamp private OffsetDateTime createdAt;

    @UpdateTimestamp private OffsetDateTime updatedAt;

    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    @JoinColumn(name = "BOOK_STORE_ID", referencedColumnName = "ID")
    private BookStore bookStore;

    public Book() {}

    public Book(String title, int pageCount) {

        this.title = title;
        this.pageCount = pageCount;
    }

    public Book(String title, int pageCount, Author author, BookStore bookStore) {
        this.title = title;
        this.pageCount = pageCount;
        this.author = author;
        this.bookStore = bookStore;
    }
}
