package com.mingyuchoo.graphql.author.controller;

import com.mingyuchoo.graphql.author.model.Author;
import com.mingyuchoo.graphql.author.repository.AuthorRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @SchemaMapping(value = "findAllAuthors", typeName = "Query")
    public Iterable<Author> findAllAuthors () {
        return authorRepository.findAll();
    }

    @SchemaMapping(value = "countAuthors", typeName = "Query")
    public Long countAuthors() {
        return authorRepository.count();
    }
}
