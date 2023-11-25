package com.mingyuchoo.graphql.repository;

import com.mingyuchoo.graphql.entity.BookStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreRepository extends JpaRepository<BookStoreEntity, Long> {}
