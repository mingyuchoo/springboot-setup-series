package com.mingyuchoo.graphql.author.repository;

import com.mingyuchoo.graphql.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
