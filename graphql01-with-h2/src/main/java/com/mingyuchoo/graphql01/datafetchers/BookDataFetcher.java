package com.mingyuchoo.graphql01.datafetchers;

import com.mingyuchoo.graphql01.entity.AuthorEntity;
import com.mingyuchoo.graphql01.entity.BookEntity;
import com.mingyuchoo.graphql01.entity.BookStoreEntity;
import com.mingyuchoo.graphql01.repository.AuthorRepository;
import com.mingyuchoo.graphql01.repository.BookRepository;
import com.mingyuchoo.graphql01.repository.BookStoreRepository;
import graphql.schema.DataFetcher;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher {

    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookStoreRepository bookStoreRepository;

    // Count
    public DataFetcher<?> bookCount() {
        return environment -> {
            return bookRepository.count();
        };
    }

    // List
    public DataFetcher<?> books() {
        return environment -> {
            return bookRepository.findAll();
        };
    }

    // Read
    public DataFetcher<?> book() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            return bookRepository.findById(id);
        };
    }

    // Create
    public DataFetcher<?> addBook() {
        return environment -> {
            String title = environment.getArgument("title");
            int pageCount = environment.getArgument("pageCount");
            return bookRepository.save(new BookEntity(title, pageCount));
        };
    }

    public DataFetcher<?> addBookWithAuthorIdAndBookStoreId() {
        return environment -> {
            String title = environment.getArgument("title");
            int pageCount = environment.getArgument("pageCount");
            Long authorId = Long.parseLong(environment.getArgument("authorId"));
            Long bookStoreId = Long.parseLong(environment.getArgument("bookStoreId"));

            Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(authorId);
            Optional<BookStoreEntity> optionalBookStoreEntity =
                    bookStoreRepository.findById(bookStoreId);

            if (optionalAuthorEntity.isPresent() && optionalBookStoreEntity.isPresent()) {
                AuthorEntity authorEntity = optionalAuthorEntity.get();
                BookStoreEntity bookStoreEntity = optionalBookStoreEntity.get();
                return bookRepository.save(
                        new BookEntity(title, pageCount, authorEntity, bookStoreEntity));
            }
            throw new NotFoundException("Not found Author or BookStore to create Book!");
        };
    }

    // Update
    public DataFetcher<?> changeBook() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            String title = environment.getArgument("title");
            Integer pageCount = Integer.parseInt(environment.getArgument("pageCount"));

            Optional<BookEntity> optBookEntity = bookRepository.findById(id);
            if (optBookEntity.isPresent()) {
                BookEntity bookEntity = optBookEntity.get();
                if (title != null) bookEntity.setTitle(title);
                if (pageCount >= 0) bookEntity.setPageCount(pageCount);
                return bookRepository.save(bookEntity);
            }
            throw new NotFoundException("Not found Book to update!");
        };
    }

    // Delete
    public DataFetcher<?> removeBook() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            bookRepository.deleteById(id);
            return id;
        };
    }
}
