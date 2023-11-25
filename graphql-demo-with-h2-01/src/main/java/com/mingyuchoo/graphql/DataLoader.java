package com.mingyuchoo.graphql;

import com.mingyuchoo.graphql.entity.AuthorEntity;
import com.mingyuchoo.graphql.entity.BookEntity;
import com.mingyuchoo.graphql.entity.BookStoreEntity;
import com.mingyuchoo.graphql.entity.CityEntity;
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
        this.cityRepository.save(new CityEntity("Seoul", 9708247));
        this.cityRepository.save(new CityEntity("New York", 8175133));
        this.cityRepository.save(new CityEntity("Los Angeles", 3792621));
        this.cityRepository.save(new CityEntity("Chicago", 2695598));
        this.cityRepository.save(new CityEntity("Houston", 2100263));
        this.cityRepository.save(new CityEntity("Seattle", 744955));

        // Author

        AuthorEntity authorHarry = new AuthorEntity("J.K.", "Rowling");
        this.authorRepository.save(authorHarry);

        this.authorRepository.save(new AuthorEntity("Hermione", "Granger"));
        this.authorRepository.save(new AuthorEntity("Ron", "Weasley"));
        this.authorRepository.save(new AuthorEntity("Rubeus", "Hagrid"));
        this.authorRepository.save(new AuthorEntity("Gellert", "Grindelwald"));
        this.authorRepository.save(new AuthorEntity("Dudley", "Dursley"));
        this.authorRepository.save(new AuthorEntity("Draco", "Malfoy"));

        // Book Store
        BookStoreEntity bookStoreAmazon = new BookStoreEntity("Amazon", "Seattle");
        this.bookStoreRepository.save(bookStoreAmazon);
        this.bookStoreRepository.save(new BookStoreEntity("교보문고", "강남"));
        this.bookStoreRepository.save(new BookStoreEntity("알라딘", "인터넷"));

        // Book
        this.bookRepository.save((new BookEntity("헤리포터", 472, authorHarry, bookStoreAmazon)));
    }
}
