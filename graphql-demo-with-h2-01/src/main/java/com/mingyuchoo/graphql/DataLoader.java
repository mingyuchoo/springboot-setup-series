package com.mingyuchoo.graphql;

import com.mingyuchoo.graphql.model.Author;
import com.mingyuchoo.graphql.model.Book;
import com.mingyuchoo.graphql.model.BookStore;
import com.mingyuchoo.graphql.model.City;
import com.mingyuchoo.graphql.repository.AuthorRepository;
import com.mingyuchoo.graphql.repository.BookRepository;
import com.mingyuchoo.graphql.repository.BookStoreRepository;
import com.mingyuchoo.graphql.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private AuthorRepository authorRepository;
    private CityRepository cityRepository;
    private BookStoreRepository bookStoreRepository;
    private BookRepository bookRepository;

    @Autowired
    public DataLoader(
            CityRepository cityRepository,
            AuthorRepository authorRepository,
            BookStoreRepository bookStoreRepository,
            BookRepository bookRepository) {
        this.cityRepository = cityRepository;
        this.authorRepository = authorRepository;
        this.bookStoreRepository = bookStoreRepository;
        this.bookRepository = bookRepository;
    }

    public void run(ApplicationArguments args) {

        // City
        this.cityRepository.save(new City("Seoul", 9708247));
        this.cityRepository.save(new City("New York", 8175133));
        this.cityRepository.save(new City("Los Angeles", 3792621));
        this.cityRepository.save(new City("Chicago", 2695598));
        this.cityRepository.save(new City("Houston", 2100263));
        this.cityRepository.save(new City("Seattle", 744955));

        // Author

        Author authorHarry = new Author("J.K.", "Rowling");
        this.authorRepository.save(authorHarry);

        this.authorRepository.save(new Author("Hermione", "Granger"));
        this.authorRepository.save(new Author("Ron", "Weasley"));
        this.authorRepository.save(new Author("Rubeus", "Hagrid"));
        this.authorRepository.save(new Author("Gellert", "Grindelwald"));
        this.authorRepository.save(new Author("Dudley", "Dursley"));
        this.authorRepository.save(new Author("Draco", "Malfoy"));

        // Book Store
        BookStore bookStoreAmazon = new BookStore("Amazon", "Seattle");
        this.bookStoreRepository.save(bookStoreAmazon);
        this.bookStoreRepository.save(new BookStore("교보문고", "강남"));
        this.bookStoreRepository.save(new BookStore("알라딘", "인터넷"));

        // Book
        this.bookRepository.save((new Book("헤리포터", 472, authorHarry, bookStoreAmazon)));
    }
}
