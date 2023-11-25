package com.mingyuchoo.web.user.repository;

import com.mingyuchoo.web.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {}
