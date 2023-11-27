package com.mingyuchoo.graphql.user.repository;

import com.mingyuchoo.graphql.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
