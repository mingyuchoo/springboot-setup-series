package com.mingyuchoo.graphql01.entity;

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
public class BookStoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeName;

    private String storeLocation;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @OneToMany
    @JoinColumn(name = "BOOK_STORE_ID", referencedColumnName = "ID")
    private List<BookEntity> books;

    public BookStoreEntity() {}

    public BookStoreEntity(String storeName, String storeLocation) {
        this.storeName = storeName;
        this.storeLocation = storeLocation;
    }
}
