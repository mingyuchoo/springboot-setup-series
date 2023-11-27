package com.mingyuchoo.graphql.role.repository;

import com.mingyuchoo.graphql.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
