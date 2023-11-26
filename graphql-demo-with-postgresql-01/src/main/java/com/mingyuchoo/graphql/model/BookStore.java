package com.mingyuchoo.graphql.model;

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
@Table(name = "bookStores")
public class BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeName;

    private String storeLocation;

    @CreationTimestamp private OffsetDateTime createdAt;

    @UpdateTimestamp private OffsetDateTime updatedAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_STORE_ID", referencedColumnName = "ID")
    private List<Book> books;

    public BookStore() {}

    public BookStore(String storeName, String storeLocation) {
        this.storeName = storeName;
        this.storeLocation = storeLocation;
    }
}
