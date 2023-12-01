package com.mingyuchoo.graphql.biz.user.repository;

import com.mingyuchoo.graphql.biz.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
