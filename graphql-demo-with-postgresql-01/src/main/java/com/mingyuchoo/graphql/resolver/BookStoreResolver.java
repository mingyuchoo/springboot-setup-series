package com.mingyuchoo.graphql.resolver;

import com.mingyuchoo.graphql.model.BookStore;
import com.mingyuchoo.graphql.repository.BookStoreRepository;
import graphql.schema.DataFetcher;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookStoreResolver {

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
            return bookStoreRepository.save(new BookStore(storeName, storeLocation));
        };
    }

    // Update
    public DataFetcher<?> changeBookStore() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            String storeName = environment.getArgument("storeName");
            String storeLocation = environment.getArgument("storeLocation");

            Optional<BookStore> optBookStoreEntity = bookStoreRepository.findById(id);
            if (optBookStoreEntity.isPresent()) {
                BookStore bookStore = optBookStoreEntity.get();
                if (storeName != null) bookStore.setStoreName(storeName);
                if (storeLocation != null) bookStore.setStoreLocation((storeLocation));
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
