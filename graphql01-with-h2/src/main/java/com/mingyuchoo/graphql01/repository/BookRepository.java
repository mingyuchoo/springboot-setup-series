package com.mingyuchoo.graphql01.repository;

import com.mingyuchoo.graphql01.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {}
