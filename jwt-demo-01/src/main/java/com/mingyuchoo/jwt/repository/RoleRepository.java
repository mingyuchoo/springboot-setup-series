package com.mingyuchoo.jwt.repository;

import com.mingyuchoo.jwt.model.ERole;
import com.mingyuchoo.jwt.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
