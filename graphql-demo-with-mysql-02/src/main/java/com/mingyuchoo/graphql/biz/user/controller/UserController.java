package com.mingyuchoo.graphql.biz.user.controller;

import com.mingyuchoo.graphql.biz.user.model.User;
import com.mingyuchoo.graphql.biz.user.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SchemaMapping(value = "findAllUsers", typeName = "Query")
    public Iterable<User> findAllUsers () {
        return userRepository.findAll();
    }

    @SchemaMapping(value = "countUsers", typeName = "Query")
    public Long countUsers() {
        return userRepository.count();
    }
}
