package com.mingyuchoo.graphql.resolver;

import com.mingyuchoo.graphql.model.Author;
import com.mingyuchoo.graphql.model.Book;
import com.mingyuchoo.graphql.model.BookStore;
import com.mingyuchoo.graphql.repository.AuthorRepository;
import com.mingyuchoo.graphql.repository.BookRepository;
import com.mingyuchoo.graphql.repository.BookStoreRepository;
import graphql.schema.DataFetcher;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookResolver {

    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookStoreRepository bookStoreRepository;

    public BookResolver() {}

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
            return bookRepository.save(new Book(title, pageCount));
        };
    }

    public DataFetcher<?> addBookWithAuthorIdAndBookStoreId() {
        return environment -> {
            String title = environment.getArgument("title");
            int pageCount = environment.getArgument("pageCount");
            Long authorId = Long.parseLong(environment.getArgument("authorId"));
            Long bookStoreId = Long.parseLong(environment.getArgument("bookStoreId"));

            Optional<Author> optionalAuthorEntity = authorRepository.findById(authorId);
            Optional<BookStore> optionalBookStoreEntity =
                    bookStoreRepository.findById(bookStoreId);

            if (optionalAuthorEntity.isPresent() && optionalBookStoreEntity.isPresent()) {
                Author author = optionalAuthorEntity.get();
                BookStore bookStore = optionalBookStoreEntity.get();
                return bookRepository.save(
                        new Book(title, pageCount, author, bookStore));
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

            Optional<Book> optBookEntity = bookRepository.findById(id);
            if (optBookEntity.isPresent()) {
                Book book = optBookEntity.get();
                if (title != null) book.setTitle(title);
                if (pageCount >= 0) book.setPageCount(pageCount);
                return bookRepository.save(book);
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
