package com.mingyuchoo.graphql01.repository;

import com.mingyuchoo.graphql01.entity.BookStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreRepository extends JpaRepository<BookStoreEntity, Long> {}
