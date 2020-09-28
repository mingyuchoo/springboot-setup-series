package com.mingyuchoo.graphql01.datafetchers;

import com.mingyuchoo.graphql01.entity.BookStoreEntity;
import com.mingyuchoo.graphql01.repository.BookStoreRepository;
import graphql.schema.DataFetcher;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookStoreDataFetcher {

    @Autowired private BookStoreRepository bookStoreRepository;

    // Count
    public DataFetcher<?> bookStoreCount() {
        return environment -> {
            return bookStoreRepository.count();
        };
    }

    // List
    public DataFetcher<?> bookStores() {
        return environment -> {
            return bookStoreRepository.findAll();
        };
    }

    // Read
    public DataFetcher<?> bookStore() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            return bookStoreRepository.findById(id);
        };
    }

    // Create
    public DataFetcher<?> addBookStore() {
        return environment -> {
            String storeName = environment.getArgument("storeName");
            String storeLocation = environment.getArgument("storeLocation");
            return bookStoreRepository.save(new BookStoreEntity(storeName, storeLocation));
        };
    }

    // Update
    public DataFetcher<?> changeBookStore() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            String storeName = environment.getArgument("storeName");
            String storeLocation = environment.getArgument("storeLocation");

            Optional<BookStoreEntity> optBookStoreEntity = bookStoreRepository.findById(id);
            if (optBookStoreEntity.isPresent()) {
                BookStoreEntity bookStoreEntity = optBookStoreEntity.get();
                if (storeName != null) bookStoreEntity.setStoreName(storeName);
                if (storeLocation != null) bookStoreEntity.setStoreLocation((storeLocation));
            }
            throw new NotFoundException("Not found BootStore to update!");
        };
    }

    // Delete
    public DataFetcher<?> removeBookStore() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            bookStoreRepository.deleteById(id);
            return id;
        };
    }
}
