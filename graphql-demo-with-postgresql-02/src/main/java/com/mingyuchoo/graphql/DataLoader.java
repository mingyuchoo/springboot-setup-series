package com.mingyuchoo.graphql;

import com.mingyuchoo.graphql.author.model.Author;
import com.mingyuchoo.graphql.author.repository.AuthorRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private AuthorRepository authorRepository;

    public DataLoader (AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public void run(ApplicationArguments applicationArguments) {
        this.authorRepository.save(new Author(1, "Adam", 1));
    }
}
