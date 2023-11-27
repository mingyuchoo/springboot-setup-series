package com.mingyuchoo.graphql.biz.role.controller;

import com.mingyuchoo.graphql.biz.role.model.Role;
import com.mingyuchoo.graphql.biz.role.repository.RoleRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @SchemaMapping(value = "findAllRoles", typeName = "Query")
    public Iterable<Role> findAllRoles () {
        return roleRepository.findAll();
    }

    @SchemaMapping(value = "countRoles", typeName = "Query")
    public Long countRoles() {
        return roleRepository.count();
    }
}
