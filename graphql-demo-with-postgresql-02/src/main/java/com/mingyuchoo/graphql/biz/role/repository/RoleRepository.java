package com.mingyuchoo.graphql.biz.role.repository;

import com.mingyuchoo.graphql.biz.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
