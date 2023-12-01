package com.mingyuchoo.graphql.biz.permission.controller;

import com.mingyuchoo.graphql.biz.permission.model.Permission;
import com.mingyuchoo.graphql.biz.permission.repository.PermissionRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @QueryMapping
    public Iterable<Permission> findAllPermissions () {
        return permissionRepository.findAll();
    }

    @QueryMapping
    public Long countPermissions() {
        return permissionRepository.count();
    }
}
