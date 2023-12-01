package com.mingyuchoo.graphql.biz.role.controller;

import com.mingyuchoo.graphql.biz.role.model.Role;
import com.mingyuchoo.graphql.biz.role.repository.RoleRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @QueryMapping
    public Iterable<Role> findAllRoles () {
        return roleRepository.findAll();
    }

    @QueryMapping
    public Long countRoles() {
        return roleRepository.count();
    }
}
