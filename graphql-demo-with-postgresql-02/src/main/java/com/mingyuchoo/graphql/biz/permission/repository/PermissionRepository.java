package com.mingyuchoo.graphql.biz.permission.repository;

import com.mingyuchoo.graphql.biz.permission.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
