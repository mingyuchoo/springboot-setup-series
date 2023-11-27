package com.mingyuchoo.graphql.permission.controller;

import com.mingyuchoo.graphql.permission.model.Permission;
import com.mingyuchoo.graphql.permission.repository.PermissionRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @SchemaMapping(value = "findAllPermissions", typeName = "Query")
    public Iterable<Permission> findAllPermissions () {
        return permissionRepository.findAll();
    }

    @SchemaMapping(value = "countPermissions", typeName = "Query")
    public Long countPermissions() {
        return permissionRepository.count();
    }
}
