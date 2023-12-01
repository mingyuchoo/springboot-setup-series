package com.mingyuchoo.graphql.biz.user.controller;

import com.mingyuchoo.graphql.biz.user.model.User;
import com.mingyuchoo.graphql.biz.user.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryMapping
    public Iterable<User> findAllUsers () {
        return userRepository.findAll();
    }

    @QueryMapping
    public Long countUsers() {
        return userRepository.count();
    }
}
