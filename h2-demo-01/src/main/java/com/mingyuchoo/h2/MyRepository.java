package com.mingyuchoo.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository<MyEntity, Integer> {
    public MyEntity findById(int id);
}
