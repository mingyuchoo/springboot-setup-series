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

        this.authorRepository.save(new Author("José", "Saramago"));
        this.authorRepository.save(new Author("Bernard", "Werber"));
        this.authorRepository.save(new Author("Conan", "Doyle"));

        Author authorShakespeare = new Author("William", "Shakespeare");
        this.authorRepository.save(authorShakespeare);

        this.authorRepository.save(new Author("Wolfgang", "Goethe"));
        this.authorRepository.save(new Author("상섭", "염"));

        // Book Store
        BookStore bookStoreAmazon = new BookStore("Amazon", "Seattle");
        this.bookStoreRepository.save(bookStoreAmazon);
        this.bookStoreRepository.save(new BookStore("교보문고", "강남"));
        this.bookStoreRepository.save(new BookStore("알라딘", "인터넷"));

        // Book
        this.bookRepository.save(new Book("헤리포터", 472, authorHarry, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Titus Andronicus", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Romeo and Juliet", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Julius Caesar", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(new Book("Hamlet", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Othello", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Timon of Athens", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Macbeth", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("King Lear", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Antony and Cleopatra", 100, authorShakespeare, bookStoreAmazon));
        this.bookRepository.save(
                new Book("Coriolanus", 100, authorShakespeare, bookStoreAmazon));
    }
}
