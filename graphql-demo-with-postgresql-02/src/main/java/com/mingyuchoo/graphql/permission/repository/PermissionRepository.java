package com.mingyuchoo.graphql.permission.repository;

import com.mingyuchoo.graphql.permission.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
