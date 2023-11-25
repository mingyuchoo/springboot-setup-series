package com.mingyuchoo.graphql.datafetchers;

import com.mingyuchoo.graphql.entity.AuthorEntity;
import com.mingyuchoo.graphql.repository.AuthorRepository;
import graphql.schema.DataFetcher;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorDataFetcher {

    @Autowired private AuthorRepository authorRepository;

    // Count
    public DataFetcher<?> authorCount() {
        return environment -> {
            return authorRepository.count();
        };
    }

    // List
    public DataFetcher<?> authors() {
        return environment -> {
            return authorRepository.findAll();
        };
    }

    // Read
    public DataFetcher<?> author() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            return authorRepository.findById(id);
        };
    }

    // Create
    public DataFetcher<?> addAuthor() {
        return environment -> {
            String firstName = environment.getArgument("firstName");
            String lastName = environment.getArgument("lastName");
            return authorRepository.save(new AuthorEntity(firstName, lastName));
        };
    }

    // Update
    public DataFetcher<?> changeAuthor() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            String firstName = environment.getArgument("firstName");
            String lastName = environment.getArgument("lastName");

            Optional<AuthorEntity> optAuthorEntity = authorRepository.findById(id);
            if (optAuthorEntity.isPresent()) {
                AuthorEntity authorEntity = optAuthorEntity.get();
                if (firstName != null) authorEntity.setFirstName(firstName);
                if (lastName != null) authorEntity.setLastName(lastName);
                return authorRepository.save(authorEntity);
            }
            throw new NotFoundException("Not found Author to update!");
        };
    }

    // Delete
    public DataFetcher<?> removeAuthor() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            authorRepository.deleteById(id);
            return true;
        };
    }
}
