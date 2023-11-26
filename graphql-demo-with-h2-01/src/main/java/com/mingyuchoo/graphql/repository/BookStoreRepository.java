package com.mingyuchoo.graphql.repository;

import com.mingyuchoo.graphql.model.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreRepository extends JpaRepository<BookStore, Long> {}
