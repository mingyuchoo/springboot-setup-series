package com.mingyuchoo.web.biz.user.repository;

import com.mingyuchoo.web.biz.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {}
